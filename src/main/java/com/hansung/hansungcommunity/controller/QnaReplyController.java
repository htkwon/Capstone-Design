package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.dto.QnaReplyDto;
import com.hansung.hansungcommunity.service.QnaReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QnaReplyController {

    private final QnaReplyService replyService;

    /**
     * 댓글 생성
     * 대댓글 생성 uri 논의 필요
     */
    @PostMapping("/qnaBoards/{boardId}/replies")
    public ResponseEntity<QnaReplyDto> create(
            @PathVariable("boardId") Long boardId,
            @RequestBody @Valid QnaReplyDto replyDto
    ) {
        Long userId = 100L; // 임시 유저 id
        QnaReplyDto reply = replyService.create(userId, boardId, replyDto);

        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    /**
     * 댓글 조회(임시)
     */
    @GetMapping("/qnaBoards/{boardId}/replies")
    public ResponseEntity<List<QnaReplyDto>> list(
            @PathVariable("boardId") Long boardId
    ) {
        List<QnaReplyDto> replies = replyService.findAllByBoardId(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(replies);
    }

}
