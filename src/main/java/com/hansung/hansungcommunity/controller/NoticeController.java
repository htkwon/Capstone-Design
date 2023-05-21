package com.hansung.hansungcommunity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.FileDto;
import com.hansung.hansungcommunity.dto.FileRequestDto;
import com.hansung.hansungcommunity.dto.NoticeBoardDto;
import com.hansung.hansungcommunity.entity.NoticeBoard;
import com.hansung.hansungcommunity.repository.AdoptRepository;
import com.hansung.hansungcommunity.repository.FileRepository;
import com.hansung.hansungcommunity.service.FileService;
import com.hansung.hansungcommunity.service.FireBaseService;
import com.hansung.hansungcommunity.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


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
     * 글 상세보기
     */
    @GetMapping("/notice/{boardId}/detail")
    public ResponseEntity<NoticeBoardDto> detail(@PathVariable("boardId") Long boardId) {
        NoticeBoardDto dto = noticeService.detail(boardId);
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
            FileDto dto = FileDto.of(noticeBoard, fileName);
            fileService.save(dto);
            fireBaseService.uploadFiles(f, fileName);

        }
        return ResponseEntity.status(HttpStatus.OK).body(boardId);
    }

    /**
     * 해당 자유게시판 게시글에서 첨부 파일이 있는지 체크
     */
    @GetMapping("/notice/{boardId}/file-check")
    public ResponseEntity<Boolean> checkFile(@PathVariable("boardId") Long boardId) {
        Boolean check = fileService.check(boardId);
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
     * 글 삭제
     */
    @DeleteMapping("/notice/{boardId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("boardId") Long boardId) {
        noticeService.delete(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
