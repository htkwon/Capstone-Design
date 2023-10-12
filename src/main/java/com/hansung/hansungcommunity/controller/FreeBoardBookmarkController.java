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
public class FreeBoardBookmarkController {

    private final BookmarkService bookmarkService;

    /**
     * 북마크 등록
     */
    @PostMapping("/free/{boardId}/bookmark")
    public ResponseEntity<Void> createBookmark(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        bookmarkService.createBookmark(boardId, ca.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 북마크 취소
     */
    @DeleteMapping("/free/{boardId}/bookmark")
    public ResponseEntity<Void> cancelBookmark(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        bookmarkService.cancelBookmark(boardId, ca.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 해당 유저가 해당 게시글을 북마크 했는지 체크
     */
    @GetMapping("/free/{boardId}/bookmark-check")
    public ResponseEntity<Boolean> checkBookmarkOfPost(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        boolean check = bookmarkService.checkBookmarkOfPost(boardId, ca.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    /**
     * 해당 게시글의 북마크수 조회
     */
    @GetMapping("/free/{boardId}/bookmark-count")
    public ResponseEntity<Integer> countBookmarkOfPost(@PathVariable("boardId") Long boardId) {
        int count = bookmarkService.countBookmarkOfPost(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

}
