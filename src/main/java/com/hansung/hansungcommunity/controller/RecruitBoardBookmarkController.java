package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecruitBoardBookmarkController {

    private final BookmarkService bookmarkService;

    /**
     * 북마크 등록
     */
    @PostMapping("/recruit/{boardId}/bookmark")
    public ResponseEntity<Void> create(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        bookmarkService.create(boardId, ca.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 북마크 취소
     */
    @DeleteMapping("/recruit/{boardId}/bookmark")
    public ResponseEntity<Void> cancel(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        bookmarkService.cancel(boardId, ca.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 해당 유저가 해당 게시글을 북마크 했는지 체크
     */
    @GetMapping("/recruit/{boardId}/bookmark-check")
    public ResponseEntity<Boolean> check(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Boolean check = bookmarkService.check(boardId, ca.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    /**
     * 해당 게시글의 북마크수 조회
     */
    @GetMapping("/recruit/{boardId}/bookmark-count")
    public ResponseEntity<Integer> count(@PathVariable("boardId") Long boardId) {
        int count = bookmarkService.count(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

}
