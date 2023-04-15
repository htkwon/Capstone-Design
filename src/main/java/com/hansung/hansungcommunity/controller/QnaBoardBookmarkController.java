package com.hansung.hansungcommunity.controller;


import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.qna.QnaBoardBookmarkDto;
import com.hansung.hansungcommunity.entity.QnaBoardBookmark;
import com.hansung.hansungcommunity.service.QnaBoardBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QnaBoardBookmarkController {

    private final QnaBoardBookmarkService qnaBoardBookmarkService;

    /**
     * 해당 유저의 북마크 글 조회
     */
    @GetMapping("/qna/bookmark")
    public ResponseEntity<List<QnaBoardBookmarkDto>> list(Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<QnaBoardBookmarkDto> list = qnaBoardBookmarkService.getBoards(ca.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * 북마크 등록
     */
    @PostMapping("/qna/{boardId}/bookmark")
    public ResponseEntity<QnaBoardBookmark> create(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        QnaBoardBookmark qnaBoardBookmark = qnaBoardBookmarkService.create(boardId, ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(qnaBoardBookmark);
    }

    /**
     * 북마크 취소
     */
    @DeleteMapping("/qna/{boardId}/bookmark")
    public ResponseEntity<String> cancle(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        qnaBoardBookmarkService.cancle(boardId, ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }

    /**
     * 해당 유저가 해당 게시글을 북마크 했는지 체크
     */
    @GetMapping("/qna/{boardId}/bookmark-check")
    public ResponseEntity<Boolean> check(@PathVariable("boardId") Long boardId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Boolean check = qnaBoardBookmarkService.check(boardId, ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    /**
     * 해당 게시글의 북마크수 조회
     */
    @GetMapping("/qna/{boardId}/bookmark-count")
    public ResponseEntity<Integer> count(@PathVariable("boardId") Long boardId) {
        int count = qnaBoardBookmarkService.count(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

}
