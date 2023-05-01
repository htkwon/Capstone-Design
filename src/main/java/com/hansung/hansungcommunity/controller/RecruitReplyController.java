package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.recruit.RecruitReplyDto;
import com.hansung.hansungcommunity.service.RecruitReplyService;
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
public class RecruitReplyController {
    private final RecruitReplyService recruitReplyService;

    @PostMapping("/recruit/{boardId}/replies")
    public ResponseEntity<RecruitReplyDto> create(
            @PathVariable("boardId") Long boardId,
            @RequestBody @Valid RecruitReplyDto replyDto,
            Authentication authentication
    ){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Long userId = ca.getUser().getId();

        RecruitReplyDto reply = recruitReplyService.create(userId, boardId, replyDto);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    @GetMapping("/recruit/{boardId}/replies")
    public ResponseEntity<List<RecruitReplyDto>> list(@PathVariable("boardId") Long boardId){
        List<RecruitReplyDto> list = recruitReplyService.getReplyList(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PutMapping("/recruit/update/replies")
    public ResponseEntity<RecruitReplyDto> update(@RequestBody RecruitReplyDto replyDto){
        RecruitReplyDto dto = recruitReplyService.update(replyDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/recruit/delete/{replyId}/replies")
    public ResponseEntity<String> delete(@PathVariable("replyId") Long replyId){
        recruitReplyService.delete(replyId);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }



}
