package com.hansung.hansungcommunity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.FileDto;
import com.hansung.hansungcommunity.dto.FileRequestDto;
import com.hansung.hansungcommunity.dto.notice.NoticeBoardDetailsDto;
import com.hansung.hansungcommunity.dto.notice.NoticeBoardDto;
import com.hansung.hansungcommunity.dto.notice.NoticeBoardListDto;
import com.hansung.hansungcommunity.dto.notice.NoticeBoardMainDto;
import com.hansung.hansungcommunity.entity.NoticeBoard;
import com.hansung.hansungcommunity.service.FileService;
import com.hansung.hansungcommunity.service.FireBaseService;
import com.hansung.hansungcommunity.service.NoticeService;
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


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeController {

    private final NoticeService noticeService;
    private final FileService fileService;
    private final FireBaseService fireBaseService;

    /**
     * 전체 글 조회
     */
    @GetMapping("/notice")
    public ResponseEntity<List<NoticeBoardDto>> getList(Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<NoticeBoardDto> dtos = noticeService.getList(ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /**
     * 메인 페이지 공지사항 조회 (게시글 4개반환)
     */
    @GetMapping("/notice/main")
    public ResponseEntity<Result<List<NoticeBoardMainDto>>> list() {
        List<NoticeBoardMainDto> dtoList = noticeService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(dtoList));
    }

    /**
     * 공지사항 목록 페이지 (해당 페이지에 개수에 맞게 데이터 반환)
     * 페이지 정보는 프론트에서 전송
     */
    @GetMapping("/notice/list")
    public ResponseEntity<ListResult<List<NoticeBoardListDto>>> listOfPage(Pageable pageable, @RequestParam(required = false) String search) {
        List<NoticeBoardListDto> dtoList = noticeService.findByPage(pageable, search);
        long count = noticeService.getCount(search);

        return ResponseEntity.status(HttpStatus.OK).body(new ListResult<>(dtoList, count));
    }

    /**
     * 글 상세보기
     */
    @GetMapping("/notice/detail/{boardId}")
    public ResponseEntity<NoticeBoardDetailsDto> detail(
            @PathVariable("boardId") Long boardId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        increaseHits(boardId, request, response);
        NoticeBoardDetailsDto dto = noticeService.detail(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 글 작성 (업로드 파일 없을 때)
     */
    @PostMapping("/notice/no-file")
    public ResponseEntity<Long> post(@Valid @RequestBody NoticeBoardDto dto, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Long id = noticeService.post(dto, ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(id);

    }

    /**
     * 글 작성(업로드 파일 있을 때)
     */
    @PostMapping("/notice")
    public ResponseEntity<Long> create(
            @RequestParam("file") MultipartFile[] file,
            String stringNotice,
            Authentication authentication
    ) throws IOException, FirebaseAuthException {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        NoticeBoard noticeBoard = new ObjectMapper().readValue(stringNotice, NoticeBoard.class);
        Long boardId = noticeService.mappingUser(ca.getUser().getId(), noticeBoard);

        for (MultipartFile f : file) {
            String fileName = f.getOriginalFilename();
            String extension = Objects.requireNonNull(f.getContentType()).split("/")[1];
            String createdName = String.valueOf(createFilename());
            String name = createdName + "." + extension;
            FileDto dto = FileDto.of(noticeBoard, fileName, name);
            fileService.save(dto);
            fireBaseService.uploadFiles(f, name);

        }
        return ResponseEntity.status(HttpStatus.OK).body(boardId);
    }

    /**
     * 해당 공지사항에서 첨부 파일이 있는지 체크
     */
    @GetMapping("/notice/{boardId}/file-check")
    public ResponseEntity<Boolean> checkFile(@PathVariable("boardId") Long boardId) {
        boolean check = fileService.check(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    /**
     * 해당 게시글에 첨부파일이 있음을 check 후, 해당 파일의 이름들 전송
     */
    @GetMapping("/notice/{boardId}/file-list")
    public ResponseEntity<List<FileRequestDto>> getFileList(@PathVariable("boardId") Long boardId) {
        List<FileRequestDto> dtos = fileService.list(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /**
     * 글 수정
     */

    @PutMapping("/notice/{boardId}/update")
    public ResponseEntity<NoticeBoardDto> update(@Valid @RequestBody NoticeBoardDto dto, @PathVariable("boardId") Long boardId) {
        NoticeBoardDto noticeBoardDto = noticeService.update(dto, boardId);
        return ResponseEntity.status(HttpStatus.OK).body(noticeBoardDto);
    }

    /**
     * 게시글 수정 (첨부파일 있을 때)
     */
    @PutMapping("/notice/update/{boardId}/file")
    public ResponseEntity<Result<NoticeBoardDto>> update(
            @PathVariable("boardId") Long boardId,
            @RequestParam("file") MultipartFile[] file,
            String stringNotice
    ) throws IOException, FirebaseAuthException {
        NoticeBoard board = new ObjectMapper().readValue(stringNotice, NoticeBoard.class);
        NoticeBoard real = noticeService.get(boardId);
        board.setId(real.getId());

        NoticeBoardDto boardDto = noticeService.update(NoticeBoardDto.from(board), boardId);

        for (MultipartFile f : file) {
            String fileName = f.getOriginalFilename();
            assert fileName != null;
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            String createdName = String.valueOf(createFilename());
            String name = createdName + "." + extension;
            FileDto dto = FileDto.of(real, fileName, name);
            fileService.save(dto);
            fireBaseService.uploadFiles(f, name);

        }
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(boardDto));
    }


    /**
     * 글 삭제
     */
    @DeleteMapping("/notice/{boardId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("boardId") Long boardId) {
        noticeService.delete(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 조회수 증가 로직
     */
    private void increaseHits(Long boardId, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("noticeBoardHits")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + boardId.toString() + "]")) {
                noticeService.increaseHits(boardId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + boardId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            noticeService.increaseHits(boardId);
            Cookie newCookie = new Cookie("noticeBoardHits", "[" + boardId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }

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
