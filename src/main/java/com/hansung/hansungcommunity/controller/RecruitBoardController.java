package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.RecruitBoardRequestDto;
import com.hansung.hansungcommunity.service.RecruitBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecruitBoardController {

    private final RecruitBoardService recruitBoardService;

    /**
     * 게시글 생성
     */
    @PostMapping("/recruit-board")
    public ResponseEntity<Long> create(@RequestBody RecruitBoardRequestDto dto, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Long savedId = recruitBoardService.post(ca.getUser().getId(), dto);

        return ResponseEntity.ok(savedId);
    }

}
