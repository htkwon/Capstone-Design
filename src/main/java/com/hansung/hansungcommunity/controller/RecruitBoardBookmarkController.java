package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.recruit.RecruitBoardBookmarkDto;
import com.hansung.hansungcommunity.service.RecruitBoardBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecruitBoardBookmarkController {

    private final RecruitBoardBookmarkService recruitBoardBookmarkService;

    /**
     * 해당 유저의 북마크 글 조회
     */
    @GetMapping("/recruit/bookmark")
    public ResponseEntity<List<RecruitBoardBookmarkDto>> list(Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<RecruitBoardBookmarkDto> list = recruitBoardBookmarkService.getBoards(ca.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * 북마크 등록
     */
    @PostMapping("/recruit/{boardId}/bookmark")
    public ResponseEntity<RecruitBoardBookmarkDto> create(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        RecruitBoardBookmarkDto dto = recruitBoardBookmarkService.create(boardId, ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 북마크 취소
     */
    @DeleteMapping("/recruit/{boardId}/bookmark")
    public ResponseEntity<Void> cancle(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        recruitBoardBookmarkService.cancel(boardId, ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 해당 유저가 해당 게시글을 북마크 했는지 체크
     */
    @GetMapping("/recruit/{boardId}/bookmark-check")
    public ResponseEntity<Boolean> check(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Boolean check = recruitBoardBookmarkService.check(boardId, ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    /**
     * 해당 게시글의 북마크수 조회
     */
    @GetMapping("/recruit/{boardId}/bookmark-count")
    public ResponseEntity<Integer> count(@PathVariable("boardId") Long boardId) {
        int count = recruitBoardBookmarkService.count(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

}
