package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.free.FreeBoardBookmarkDto;
import com.hansung.hansungcommunity.entity.FreeBoardBookmark;
import com.hansung.hansungcommunity.service.FreeBoardBookmarkService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FreeBoardBookmarkController {

    private final FreeBoardBookmarkService freeBoardBookmarkService;

    /**
     * 해당 유저의 북마크 글 조회
     */
    @GetMapping("/free-boards/bookmark")
    public ResponseEntity<List<FreeBoardBookmarkDto>> list(Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<FreeBoardBookmarkDto> list = freeBoardBookmarkService.getBoards(ca.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * 북마크 등록
     */
    @PostMapping("/free-boards/{boardId}/bookmark")
    public ResponseEntity<FreeBoardBookmark> create(@PathVariable("boardId") Long boardId, Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        FreeBoardBookmark freeBoardBookmark = freeBoardBookmarkService.create(boardId,ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(freeBoardBookmark);
    }

    /**
     * 북마크 취소
     */
    @DeleteMapping("/free-boards/{boardId}/bookmark")
    public ResponseEntity<String> cancle(@PathVariable("boardId") Long boardId, Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        freeBoardBookmarkService.cancle(boardId,ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }

    /**
     * 해당 유저가 해당 게시글을 북마크 했는지 체크
     */
    @GetMapping("/free-boards/{boardId}/bookmark-check")
    public ResponseEntity<Boolean> check(@PathVariable("boardId") Long boardId, Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Boolean check = freeBoardBookmarkService.check(boardId,ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    /**
     * 해당 게시글의 북마크수 조회
     */
    @GetMapping("/free-boards/{boardId}/bookmark-count")
    public ResponseEntity<Integer> count(@PathVariable("boardId") Long boardId){
        int count = freeBoardBookmarkService.count(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }


}
