package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/api/users")
    public ResponseEntity<Long> saveUser(@RequestBody UserDto dto) { // api 통신 시, Entity 가 아닌 DTO 로 주고받기 !!!
        User user = new User();
        user.setName(dto.getName());
        user.setNickname(dto.getNickname());

        Long userId = userService.join(user);

        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    // 유저 DTO
    @Data
    static class UserDto {
        private String name;
        private String nickname;
    }
}
