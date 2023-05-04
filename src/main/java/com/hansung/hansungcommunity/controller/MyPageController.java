package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.user.UserActivityDto;
import com.hansung.hansungcommunity.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/user/reply/mypage")
    public ResponseEntity<List<UserActivityDto>> replyList(Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;

        List<UserActivityDto> dtos = myPageService.getReplyList(ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);

    }

    @GetMapping("/user/post/mypage")
    public ResponseEntity<List<UserActivityDto>> boardList(Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;

        List<UserActivityDto> dtos = myPageService.getBoardList(ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/user/bookmark/mypage")
    public ResponseEntity<List<UserActivityDto>> bookmarkList(Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<UserActivityDto> dtos = myPageService.getBookmarkList(ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }




}
