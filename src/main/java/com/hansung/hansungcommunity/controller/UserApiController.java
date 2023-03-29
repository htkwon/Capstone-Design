package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.dto.UserRequestDto;
import com.hansung.hansungcommunity.service.UserService;
import lombok.Data;
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
    public ResponseEntity<Long> saveUser(@RequestBody UserRequestDto dto) { // api 통신 시, Entity 가 아닌 DTO 로 주고받기 !!!
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

}
