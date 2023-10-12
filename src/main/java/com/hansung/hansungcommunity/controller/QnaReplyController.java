package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.ReplyDto;
import com.hansung.hansungcommunity.dto.qna.QnaReplyAdoptCheckDto;
import com.hansung.hansungcommunity.dto.qna.QnaReplyDto;
import com.hansung.hansungcommunity.service.QnaReplyService;
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
public class QnaReplyController {

    private final QnaReplyService qnaReplyService;
    private final ReplyService replyService;

    /**
     * 댓글 생성
     * 대댓글 생성 uri 논의 필요
     */
    @PostMapping("/questions/{boardId}/replies")
    public ResponseEntity<QnaReplyDto> create(
            @PathVariable("boardId") Long boardId,
            @Valid @RequestBody QnaReplyDto replyDto,
            Authentication authentication
    ) {

        CustomAuthentication ca = (CustomAuthentication) authentication;
        QnaReplyDto reply = qnaReplyService.create(ca.getUser().getId(), boardId, replyDto);

        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    /**
     * 댓글 조회(임시)
     */
    @GetMapping("/questions/{boardId}/replies")
    public ResponseEntity<List<ReplyDto>> list(
            @PathVariable("boardId") Long boardId
    ) {
        List<ReplyDto> replies = replyService.getReplyList(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(replies);
    }

    /**
     * 수정, 삭제
     */
    @PutMapping("/questions/update/replies")
    public ResponseEntity<QnaReplyDto> update(@Valid @RequestBody QnaReplyDto replyDto) {
        QnaReplyDto dto = qnaReplyService.update(replyDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/questions/delete/{replyId}/replies")
    public ResponseEntity<Void> delete(@PathVariable("replyId") Long replyId) {
        qnaReplyService.delete(replyId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 채택
     */
    @PostMapping("/questions/{replyId}/adopt-replies")
    public ResponseEntity<Boolean> adopt(@PathVariable("replyId") Long replyId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        boolean adopt = qnaReplyService.adopt(replyId, ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(adopt);
    }

    /**
     * 채택 취소
     */
    @PutMapping("/questions/{replyId}/adopt-cancel")
    public ResponseEntity<Long> cancel(@PathVariable("replyId") Long replyId) {
        Long id = qnaReplyService.cancel(replyId);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }


    /**
     * 해당 id 게시글의 댓글들중 이미 채택한 댓글이 있는지 체크
     * 프론트단에서 체크 후, true면 이미 해당 글의 댓글을 채택하셨다는 알림
     * false면 채택 가능
     */
    @GetMapping("/questions/{boardId}/adopt-check")
    public ResponseEntity<QnaReplyAdoptCheckDto> adoptCheck(@PathVariable("boardId") Long boardId) {
        QnaReplyAdoptCheckDto dto = qnaReplyService.adoptCheck(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

}
