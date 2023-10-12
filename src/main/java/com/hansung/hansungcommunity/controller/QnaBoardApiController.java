package com.hansung.hansungcommunity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.FileDto;
import com.hansung.hansungcommunity.dto.FileRequestDto;
import com.hansung.hansungcommunity.dto.qna.*;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.service.FileService;
import com.hansung.hansungcommunity.service.FireBaseService;
import com.hansung.hansungcommunity.service.QnaBoardService;
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
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class QnaBoardApiController {

    private final QnaBoardService qnaBoardService;
    private final FileService fileService;
    private final FireBaseService fireBaseService;

    /**
     * 게시글 리스트 조회 - 홈 View 전용 (게시글 4개 반환)
     */
    @GetMapping("/questions/main")
    public ResponseEntity<Result<List<QnaBoardMainDto>>> getList() {
        List<QnaBoardMainDto> dtoList = qnaBoardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(dtoList));
    }

    /**
     * 특정 게시글 조회
     * 조회수 증가 로직 구현을 위해 임의로 구현, 추후 수정
     */
    @GetMapping("/questions/detail/{boardId}")
    public ResponseEntity<QnaBoardDetailsDto> getDetailedPost(
            @PathVariable("boardId") Long boardId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        increaseHits(boardId, request, response);
        QnaBoardDetailsDto boardDto = qnaBoardService.findOne(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(boardDto);
    }

    /**
     * 게시글 수정 시, 게시글 상세 정보 조회
     */
    @GetMapping("/questions/update/{boardId}")
    public ResponseEntity<QnaBoardUpdateDto> getUpdatingData(@PathVariable("boardId") Long boardId) {
        QnaBoardUpdateDto boardDto = qnaBoardService.findOneForUpdate(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(boardDto);
    }

    /**
     * Qna 게시판 목록 페이지 (해당 페이지에 개수에 맞게 데이터 반환)
     * 페이지 정보는 프론트에서 전송
     */
    @GetMapping("/questions/list")
    public ResponseEntity<ListResult<List<QnaBoardListDto>>> getListOfPage(Pageable pageable, @RequestParam(required = false) String search) {
        List<QnaBoardListDto> dtoList = qnaBoardService.findByPage(pageable, search);
        long count = qnaBoardService.getCount(search);

        return ResponseEntity.status(HttpStatus.OK).body(new ListResult<>(dtoList, count));
    }

    /**
     * 게시글 저장 (업로드 파일 없을 때)
     */
    @PostMapping("/questions/no-file")
    public ResponseEntity<Long> createNonFilePost(@Valid @RequestBody QnaBoardRequestDto dto, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Long savedId = qnaBoardService.post(ca.getUser().getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(savedId);
    }

    /**
     * 게시글 저장 (업로드 파일 있을 때)
     */
    @PostMapping("/questions")
    public ResponseEntity<Long> createPostWithFile(
            @RequestParam("file") MultipartFile[] file,
            String stringQna,
            Authentication authentication
    ) throws IOException, FirebaseAuthException {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        QnaBoard board = new ObjectMapper().readValue(stringQna, QnaBoard.class);
        Long id = qnaBoardService.mappingUser(ca.getUser().getId(), board);

        for (MultipartFile f : file) {
            String fileName = f.getOriginalFilename();
            String extension = Objects.requireNonNull(f.getContentType()).split("/")[1];
            String createdName = String.valueOf(createFilename());
            String name = createdName + "." + extension;
            FileDto dto = FileDto.of(board, fileName, name);
            fileService.saveFile(dto);
            fireBaseService.uploadFiles(f, name);
        }
        return ResponseEntity.status(HttpStatus.OK).body(id);

    }

    /**
     * 해당 Q&A게시판 게시글에서 첨부 파일이 있는지 체크
     */
    @GetMapping("/questions/{boardId}/file-check")
    public ResponseEntity<Boolean> checkFile(@PathVariable("boardId") Long boardId) {
        boolean check = fileService.checkFileOfPost(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    /**
     * 해당 게시글에 첨부파일이 있음을 check 후, 해당 파일의 이름들 전송
     */
    @GetMapping("/questions/{boardId}/file-list")
    public ResponseEntity<List<FileRequestDto>> getFileList(@PathVariable("boardId") Long boardId) {
        List<FileRequestDto> dtos = fileService.getListOfFile(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/questions/update/{boardId}")
    public ResponseEntity<Long> updateNonFilePost(
            @PathVariable("boardId") Long boardId,
            @Valid @RequestBody QnaBoardRequestDto dto
    ) {
        Long id = qnaBoardService.update(boardId, dto);

        return ResponseEntity.ok(id);
    }

    /**
     * 게시글 수정 (첨부파일 있을 때)
     */
    @PutMapping("/questions/update/{boardId}/file")
    public ResponseEntity<Long> updatePostWithFile(
            @PathVariable("boardId") Long boardId,
            @RequestParam("file") MultipartFile[] file,
            String stringQna
    ) throws IOException, FirebaseAuthException {
        QnaBoard board = new ObjectMapper().readValue(stringQna, QnaBoard.class);
        QnaBoard real = qnaBoardService.get(boardId);
        board.setId(real.getId());

        Long id = qnaBoardService.update(boardId, QnaBoardRequestDto.from(board));

        for (MultipartFile f : file) {
            String fileName = f.getOriginalFilename();
            assert fileName != null;
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            String createdName = String.valueOf(createFilename());
            String name = createdName + "." + extension;
            FileDto dto = FileDto.of(real, fileName, name);
            fileService.saveFile(dto);
            fireBaseService.uploadFiles(f, name);

        }
        return ResponseEntity.status(HttpStatus.OK).body(id);

    }


    /**
     * 게시글 삭제
     */
    @DeleteMapping("/questions/delete/{boardId}")
    public ResponseEntity<Long> deletePost(@PathVariable("boardId") Long boardId) {
        qnaBoardService.delete(boardId);

        return ResponseEntity.ok(boardId);
    }

    @GetMapping("/questions/return/user-id/{boardId}")
    public ResponseEntity<Long> getUserId(@PathVariable("boardId") Long boardId) {
        Long userId = qnaBoardService.getUserId(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    /**
     * 조회수 증가 로직
     */
    private void increaseHits(Long boardId, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("qnaBoardHits")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + boardId.toString() + "]")) {
                qnaBoardService.increaseHits(boardId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + boardId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            qnaBoardService.increaseHits(boardId);
            Cookie newCookie = new Cookie("qnaBoardHits", "[" + boardId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }

    /**
     * 파일 이름 생성
     */
    public int createFilename() {
        Random random = new Random();
        return random.nextInt(1000);
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class ListResult<T> {
        private T data;
        private long count;
    }

}
