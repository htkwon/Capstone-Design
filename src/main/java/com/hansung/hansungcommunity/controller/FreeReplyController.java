package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.ReplyDto;
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
public class FreeReplyController {

    private final ReplyService replyService;

    @PostMapping("/free/{boardId}/replies")
    public ResponseEntity<ReplyDto> create(
            @PathVariable("boardId") Long boardId,
            @Valid @RequestBody ReplyDto replyDto,
            Authentication authentication
    ) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Long userId = ca.getUser().getId();

        ReplyDto reply = replyService.create(userId, boardId, replyDto);

        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    @GetMapping("/free/{boardId}/replies")
    public ResponseEntity<List<ReplyDto>> list(@PathVariable("boardId") Long boardId) {
        List<ReplyDto> list = replyService.getReplyList(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PutMapping("/free/update/replies")
    public ResponseEntity<ReplyDto> update(@Valid @RequestBody ReplyDto replyDto) {
        ReplyDto dto = replyService.update(replyDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/free/delete/{replyId}/replies")
    public ResponseEntity<String> delete(@PathVariable("replyId") Long replyId) {
        replyService.delete(replyId);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }

}
