package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.NoticeBoardDto;
import com.hansung.hansungcommunity.service.NoticeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 전체 글 조회
     */
    @GetMapping("/notice")
    public ResponseEntity<List<NoticeBoardDto>> getList(Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<NoticeBoardDto> dtos = noticeService.getList(ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /**
     * 글 상세보기
     */
    @GetMapping("/notice/{boardId}/detail")
    public ResponseEntity<NoticeBoardDto> detail(@PathVariable("boardId") Long boardId){
        NoticeBoardDto dto = noticeService.detail(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 글 작성
     */
    @PostMapping("/notice/post")
    public ResponseEntity<NoticeBoardDto> post(@RequestBody NoticeBoardDto dto, Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        NoticeBoardDto noticeBoardDto = noticeService.post(dto,ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(noticeBoardDto);

    }

    /**
     * 글 수정
     */

    @PutMapping("/notice/{boardId}/update")
    public ResponseEntity<NoticeBoardDto> update(@RequestBody NoticeBoardDto dto, @PathVariable("boardId") Long boardId){
        NoticeBoardDto noticeBoardDto = noticeService.update(dto,boardId);
        return ResponseEntity.status(HttpStatus.OK).body(noticeBoardDto);
    }


    /**
     * 글 삭제
     */
    @DeleteMapping("/notice/{boardId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("boardId") Long boardId){
        noticeService.delete(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }




}
