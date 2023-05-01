package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.free.FreeReplyDto;
import com.hansung.hansungcommunity.dto.qna.QnaReplyDto;
import com.hansung.hansungcommunity.repository.QnaReplyRepository;
import com.hansung.hansungcommunity.service.QnaReplyService;
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

    private final QnaReplyService replyService;

    /**
     * 댓글 생성
     * 대댓글 생성 uri 논의 필요
     */
    @PostMapping("/qna/{boardId}/replies")
    public ResponseEntity<QnaReplyDto> create(
            @PathVariable("boardId") Long boardId,
            @RequestBody @Valid QnaReplyDto replyDto,
            Authentication authentication
    ) {

        CustomAuthentication ca = (CustomAuthentication) authentication;
        QnaReplyDto reply = replyService.create(ca.getUser().getId(), boardId, replyDto);

        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    /**
     * 댓글 조회(임시)
     */
    @GetMapping("/qna/{boardId}/replies")
    public ResponseEntity<List<QnaReplyDto>> list(
            @PathVariable("boardId") Long boardId
    ) {
        List<QnaReplyDto> replies = replyService.findAllByBoardId(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(replies);
    }

    /**
     * 수정, 삭제
     */
    @PutMapping("/qna/update/replies")
    public ResponseEntity<QnaReplyDto> update(@RequestBody QnaReplyDto replyDto){
        QnaReplyDto dto = replyService.update(replyDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 채택
     */
    @PostMapping("/qna/{replyId}/adopt-replies")
    public ResponseEntity<Boolean> adopt(@PathVariable("replyId") Long replyId, Authentication authentication){
        Boolean adopt = replyService.adopt(replyId);
        return ResponseEntity.status(HttpStatus.OK).body(adopt);
    }

    /**
     * 채택 취소
     */
    @PutMapping("/qna/{replyId}/adopt-cancel")
    public ResponseEntity<Long> cancel(@PathVariable("replyId") Long replyId){
        Long id = replyService.cancel(replyId);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }


    /**
     * 해당 id 게시글의 댓글들중 이미 채택한 댓글이 있는지 체크
     * 프론트단에서 체크 후, true면 이미 해당 글의 댓글을 채택하셨다는 알림
     * false면 채택 가능
     */
    @GetMapping("/qna/{boardId}/adopt-check")
    public ResponseEntity<Boolean> adoptCheck(@PathVariable("boardId") Long boardId) {
        Boolean check = replyService.adoptCheck(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

}
