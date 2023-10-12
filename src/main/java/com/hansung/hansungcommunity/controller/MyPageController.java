package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.user.UserActivityDto;
import com.hansung.hansungcommunity.dto.user.UserUpdateDto;
import com.hansung.hansungcommunity.service.MyPageService;
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
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * 마이페이지 (접속 유저 작성 글 조회)
     */
    @GetMapping("/user/post/mypage")
    public ResponseEntity<List<UserActivityDto>> getPostList(Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;

        List<UserActivityDto> dtos = myPageService.getBoardList(ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /**
     * 마이페이지 (접속 유저 북마크 글 조회)
     */
    @GetMapping("/user/bookmark/mypage")
    public ResponseEntity<List<UserActivityDto>> getBookmarkList(Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<UserActivityDto> dtos = myPageService.getBookmarkList(ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /**
     * 마이페이지 (접속 유저가 신청한 구인게시글 조회)
     */
    @GetMapping("/user/application/mypage")
    public ResponseEntity<List<UserActivityDto>> getApplicationList(Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<UserActivityDto> dtos = myPageService.getApplicationList(ca.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /**
     * 마이페이지 (접속 유저 자기 소개 및 관심 기술 수정)
     */
    @PutMapping("/user/update")
    public ResponseEntity<UserUpdateDto> updateUserInfo(@RequestBody @Valid UserUpdateDto dto, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        myPageService.updateUserInfo(dto, ca.getUser().getId());

        return ResponseEntity.ok(dto);
    }

}
