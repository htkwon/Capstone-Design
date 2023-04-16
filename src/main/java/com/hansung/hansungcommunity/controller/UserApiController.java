package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.user.UserInfoDto;
import com.hansung.hansungcommunity.dto.user.UserRequestDto;
import com.hansung.hansungcommunity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    /**
     * 유저 저장
     */
    @PostMapping("/api/join")
    public ResponseEntity<Long> saveUser(Authentication authentication, @RequestBody UserRequestDto dto) { // api 통신 시, Entity 가 아닌 DTO 로 주고받기 !!!
        if (!dto.getStudentId().equals(authentication.getName())) {
            throw new IllegalArgumentException("인가 서버와의 학번이 동일하지 않습니다.");
        }

        Long userId = userService.join(dto);

        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    /**
     * 유저 검증
     */
    @GetMapping("/api/check")
    public ResponseEntity<Boolean> checkUser(Authentication authentication){
        String stuId = authentication.getName();
        boolean check = userService.checkUser(stuId);

        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    /**
     * 유저 정보 반환
     */
    @GetMapping("/api/user-info")
    public ResponseEntity<UserInfoDto> userInfo(Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        UserInfoDto userInfoDto = userService.getUserInfo(ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(userInfoDto);
    }

    /**
     * 현재 접속 유저의 id만 반환 (프론트 댓글, 대댓글에서 사용)
     */
    @GetMapping("/api/user-id")
    public ResponseEntity<Long> userId(Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        return ResponseEntity.status(HttpStatus.OK).body(ca.getUser().getId());
    }


}




