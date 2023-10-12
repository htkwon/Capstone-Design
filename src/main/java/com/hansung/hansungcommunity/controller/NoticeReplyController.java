package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.common.ReplyDto;
import com.hansung.hansungcommunity.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeReplyController {

    private final ReplyService replyService;

    @PostMapping("/notice/{boardId}/replies")
    public ResponseEntity<ReplyDto> createReply(
            @PathVariable("boardId") Long boardId,
            @Valid @RequestBody ReplyDto replyDto,
            Authentication authentication
    ) {

        CustomAuthentication ca = (CustomAuthentication) authentication;
        Long userId = ca.getUser().getId();

        ReplyDto reply = replyService.createReply(userId, boardId, replyDto);

        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    @GetMapping("/notice/{boardId}/replies")
    public ResponseEntity<List<ReplyDto>> getListOfReply(@PathVariable("boardId") Long boardId) {
        List<ReplyDto> list = replyService.getListOfReply(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PutMapping("/notice/update/replies")
    public ResponseEntity<ReplyDto> updateReply(@Valid @RequestBody ReplyDto replyDto) {
        ReplyDto dto = replyService.updateReply(replyDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/notice/delete/{replyId}/replies")
    public ResponseEntity<String> deleteReply(@PathVariable("replyId") Long replyId) {
        replyService.deleteReply(replyId);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }

}
