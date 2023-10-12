package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.dto.common.BoardMainDto;
import com.hansung.hansungcommunity.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    /**
     * 인기 게시글 반환
     * 조회수를 기준으로 5개의 게시글 반환
     */
    @GetMapping("/popular")
    public ResponseEntity<List<BoardMainDto>> getPopularBoards() {
        List<BoardMainDto> boards = boardService.getPopularBoards();

        return ResponseEntity.ok(boards);
    }

}
