package com.hansung.hansungcommunity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.FileDto;
import com.hansung.hansungcommunity.dto.FileRequestDto;
import com.hansung.hansungcommunity.dto.recruit.*;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import com.hansung.hansungcommunity.service.FileService;
import com.hansung.hansungcommunity.service.FireBaseService;
import com.hansung.hansungcommunity.service.RecruitBoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecruitBoardController {

    private final RecruitBoardService recruitBoardService;
    private final FileService fileService;
    private final FireBaseService fireBaseService;

    /**
     * 메인페이지 리스트 조회
     */
    @GetMapping("/recruit/main")
    public ResponseEntity<Result<List<RecruitBoardMainDto>>> list() {
        List<RecruitBoardMainDto> dtoList = recruitBoardService.getMainList();
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(dtoList));
    }

    /**
     * 게시글 생성 (업로드 파일 없을 때)
     */
    @PostMapping("/recruit/no-file")
    public ResponseEntity<Long> create(@RequestBody RecruitBoardRequestDto dto, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Long savedId = recruitBoardService.post(ca.getUser().getId(), dto);

        return ResponseEntity.ok(savedId);
    }

    /**
     * 게시글 생성 (업로드 파일 있을 때)
     */
    @PostMapping("/recruit")
    public ResponseEntity<Long> create(
            @RequestParam("file") MultipartFile[] file,
            String stringRecruit,
            Authentication authentication
    ) throws IOException, FirebaseAuthException {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        RecruitBoard recruitBoard = new ObjectMapper().readValue(stringRecruit, RecruitBoard.class);
        Long id = recruitBoardService.mappingUser(ca.getUser().getId(), recruitBoard);

        for (MultipartFile f : file) {
            String fileName = f.getOriginalFilename();
            FileDto dto = FileDto.of(recruitBoard, fileName);
            fileService.save(dto);
            fireBaseService.uploadFiles(f, fileName);
        }
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }


    /**
     * 해당 구인게시판 게시글에서 첨부 파일이 있는지 체크
     */
    @GetMapping("/recruit/{boardId}/file-check")
    public ResponseEntity<Boolean> checkFile(@PathVariable("boardId") Long boardId) {
        String boardType = "recruit";
        Boolean check = fileService.check(boardId, boardType);
        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    /**
     * 해당 게시글에 첨부파일이 있음을 check 후, 해당 파일의 이름들 전송
     */
    @GetMapping("/recruit/{boardId}/file-list")
    public ResponseEntity<List<FileRequestDto>> getFileList(@PathVariable("boardId") Long boardId) {
        String boardType = "recruit";
        List<FileRequestDto> dtos = fileService.list(boardId, boardType);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/recruit/update/{boardId}")
    public ResponseEntity<Long> update(
            @PathVariable("boardId") Long boardId,
            @RequestBody RecruitBoardRequestDto dto
    ) {
        Long id = recruitBoardService.update(boardId, dto);

        return ResponseEntity.ok(id);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/recruit/delete/{boardId}")
    public ResponseEntity<Long> delete(@PathVariable("boardId") Long boardId) {
        recruitBoardService.delete(boardId);

        return ResponseEntity.ok(boardId);
    }

    /**
     * 전체 게시글 수
     */
    @GetMapping("/recruit/total")
    public ResponseEntity<Long> getTotal() {
        return ResponseEntity.ok(recruitBoardService.getTotal());
    }

    /**
     * 게시글 목록 조회
     */
    @GetMapping("/recruit/list")
    public ResponseEntity<List<RecruitBoardListDto>> list(Pageable pageable, @RequestParam(required = false) String search) {
        List<RecruitBoardListDto> recruitList = recruitBoardService.getList(pageable, search);

        return ResponseEntity.ok(recruitList);
    }

    /**
     * 게시글 상세 조회
     */
    @GetMapping("/recruit/detail/{boardId}")
    public ResponseEntity<RecruitBoardDetailDto> detail(
            @PathVariable("boardId") Long boardId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        increaseHits(boardId, request, response);
        RecruitBoardDetailDto detailDto = recruitBoardService.getDetail(boardId);

        return ResponseEntity.ok(detailDto);
    }

    /**
     * 게시글 수정 시, 게시글 상세 정보 조회
     */
    @GetMapping("/recruit/update/{boardId}")
    public ResponseEntity<RecruitBoardUpdateDto> detailForUpdate(@PathVariable("boardId") Long boardId) {
        RecruitBoardUpdateDto detailDto = recruitBoardService.getDetailForUpdate(boardId);

        return ResponseEntity.ok(detailDto);
    }

    /**
     * 팀 소속 신청
     */
    @PostMapping("/recruit/{boardId}/application")
    public ResponseEntity<Long> apply(
            @PathVariable("boardId") Long boardId,
            Authentication authentication,
            @RequestBody RecruitBoardApplyRequestDto dto
    ) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Long partyId = recruitBoardService.apply(boardId, ca.getUser().getId(), dto);

        return ResponseEntity.ok(partyId);
    }

    /**
     * 팀 소속 신청 취소
     */
    @DeleteMapping("/recruit/{boardId}/application-cancel")
    public ResponseEntity<String> cancelApplication(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        boolean result = recruitBoardService.cancelApplication(boardId, ca.getUser().getId());

        if (result) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().body("이미 승인된 신청이거나, 모집이 마감된 게시글입니다.");
    }

    /**
     * 팀 소속 신청 승인
     */
    @PutMapping("/recruit/{boardId}/approval/{targetUserId}")
    public ResponseEntity<Boolean> approve(
            @PathVariable("boardId") Long boardId,
            @PathVariable("targetUserId") Long targetUserId,
            Authentication authentication
    ) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        boolean status = recruitBoardService.approve(boardId, ca.getUser().getId(), targetUserId);

        return ResponseEntity.ok(status);
    }

    /**
     * 팀 소속 신청 승인 취소
     */
    @PutMapping("/recruit/{boardId}/disapproval/{targetUserId}")
    public ResponseEntity<Boolean> disapprove(
            @PathVariable("boardId") Long boardId,
            @PathVariable("targetUserId") Long targetUserId,
            Authentication authentication
    ) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        boolean status = recruitBoardService.disapprove(boardId, ca.getUser().getId(), targetUserId);

        return ResponseEntity.ok(status);
    }

    /**
     * 모집 완료
     */
    @PutMapping("/recruit/{boardId}/complete")
    public ResponseEntity<Void> complete(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        recruitBoardService.complete(boardId, ca.getUser().getId());

        return ResponseEntity.ok().build();
    }

    /**
     * 신청자 목록
     *
     * @return 유저 ID, 유저 닉네임, 필수/우대 사항 충족 여부, 프로필 사진, 학번, 1트랙, 관심 기술
     */
    @GetMapping("/recruit/{boardId}/applicants")
    public ResponseEntity<List<ApplicantDto>> getApplicants(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<ApplicantDto> applicants = recruitBoardService.getApplicants(boardId, ca.getUser().getId());

        return ResponseEntity.ok(applicants);
    }

    /**
     * 신청 인원
     */
    @GetMapping("/recruit/{boardId}/applicants-number")
    public ResponseEntity<Long> getApplicantsNumber(@PathVariable("boardId") Long boardId) {
        Long number = recruitBoardService.getApplicantsNumber(boardId);

        return ResponseEntity.ok(number);
    }

    /**
     * 승인된 인원
     */
    @GetMapping("/recruit/{boardId}/approvers-number")
    public ResponseEntity<Long> getApproversNumber(@PathVariable("boardId") Long boardId) {
        Long number = recruitBoardService.getApproversNumber(boardId);

        return ResponseEntity.ok(number);
    }

    /**
     * 신청 여부 확인
     */
    @GetMapping("/recruit/{boardId}/application-check")
    public ResponseEntity<Boolean> applicationCheck(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Boolean result = recruitBoardService.applicationCheck(boardId, ca.getUser().getId());

        return ResponseEntity.ok(result);
    }

    /**
     * 조회수 증가 로직
     */
    private void increaseHits(Long boardId, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("recruitBoardHits")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + boardId.toString() + "]")) {
                recruitBoardService.increaseHits(boardId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + boardId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            recruitBoardService.increaseHits(boardId);
            Cookie newCookie = new Cookie("recruitBoardHits", "[" + boardId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
