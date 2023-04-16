package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.free.FreeReplyDto;
import com.hansung.hansungcommunity.service.FreeReplyService;
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


    private final FreeReplyService freeReplyService;

    @PostMapping("/free/{boardId}/replies")
    public ResponseEntity<FreeReplyDto> create(
            @PathVariable("boardId") Long boardId,
            @RequestBody @Valid FreeReplyDto replyDto,
            Authentication authentication
            ){

        CustomAuthentication ca = (CustomAuthentication) authentication;
        Long userId = ca.getUser().getId();

        FreeReplyDto reply = freeReplyService.create(userId, boardId, replyDto);
        return ResponseEntity.status(HttpStatus.OK).body(reply);


    }

    @GetMapping("/free/{boardId}/replies")
    public ResponseEntity<List<FreeReplyDto>> list(@PathVariable("boardId") Long boardId ){
        List<FreeReplyDto> list = freeReplyService.getReplyList(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PutMapping("/free/update/replies")
    public ResponseEntity<String> update(@RequestBody  FreeReplyDto replyDto){
        freeReplyService.update(replyDto);
        return ResponseEntity.status(HttpStatus.OK).body("수정 완료");
    }

    @DeleteMapping("/free/delete/{replyId}/replies")
    public ResponseEntity<String> delete(@PathVariable("replyId") Long replyId){
        freeReplyService.delete(replyId);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }

}
