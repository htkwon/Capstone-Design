package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.notice.NoticeReplyDto;
import com.hansung.hansungcommunity.service.NoticeReplyService;
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

    private final NoticeReplyService noticeReplyService;

    @PostMapping("/notice/{boardId}/replies")
    public ResponseEntity<NoticeReplyDto> create(
            @PathVariable("boardId") Long boardId,
            @Valid @RequestBody NoticeReplyDto replyDto,
            Authentication authentication
    ) {

        CustomAuthentication ca = (CustomAuthentication) authentication;
        Long userId = ca.getUser().getId();

        NoticeReplyDto reply = noticeReplyService.create(userId, boardId, replyDto);

        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    @GetMapping("/notice/{boardId}/replies")
    public ResponseEntity<List<NoticeReplyDto>> list(@PathVariable("boardId") Long boardId) {
        List<NoticeReplyDto> list = noticeReplyService.getReplyList(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PutMapping("/notice/update/replies")
    public ResponseEntity<NoticeReplyDto> update(@Valid @RequestBody NoticeReplyDto replyDto) {
        NoticeReplyDto dto = noticeReplyService.update(replyDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/notice/delete/{replyId}/replies")
    public ResponseEntity<String> delete(@PathVariable("replyId") Long replyId) {
        noticeReplyService.delete(replyId);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }

}
