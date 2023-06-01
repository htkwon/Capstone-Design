package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.user.*;
import com.hansung.hansungcommunity.exception.InvalidAccessException;
import com.hansung.hansungcommunity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    /**
     * 유저 저장
     */
    @PostMapping("/api/join")
    public ResponseEntity<Long> saveUser(Authentication authentication, @RequestBody @Valid UserRequestDto dto) { // api 통신 시, Entity 가 아닌 DTO 로 주고받기 !!!
        if (!dto.getStudentId().equals(authentication.getName())) {
            throw new InvalidAccessException("인가 서버와의 학번이 동일하지 않습니다.");
        }

        Long userId = userService.join(dto);

        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    /**
     * 유저 검증
     */
    @GetMapping("/api/check")
    public ResponseEntity<Boolean> checkUser(Authentication authentication) {
        String stuId = authentication.getName();
        boolean check = userService.checkUser(stuId);

        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    /**
     * 유저 정보 반환
     */
    @GetMapping("/api/user-info")
    public ResponseEntity<UserInfoDto> userInfo(Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        UserInfoDto userInfoDto = userService.getUserInfo(ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(userInfoDto);
    }

    /**
     * 단순 유저 정보
     */
    @GetMapping("/api/user-info/simple")
    public ResponseEntity<SimpleUserInfoDto> simpleUserInfo(Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;

        return ResponseEntity.ok(new SimpleUserInfoDto(ca.getUser()));
    }

    /**
     * 현재 접속 유저의 id만 반환 (프론트 댓글, 대댓글에서 사용)
     */
    @GetMapping("/api/user-id")
    public ResponseEntity<Long> userId(Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        return ResponseEntity.status(HttpStatus.OK).body(ca.getUser().getId());
    }

    /**
     * 메인페이지( 유저 채택 순위 TOP5 )
     */
    @GetMapping("/api/user-rank")
    public ResponseEntity<List<UserRankDto>> userRank() {
        List<UserRankDto> dto = userService.getUserRank();
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 유저 닉네임 검사 (추가정보 페이지)
     */
    @PostMapping("/api/user/check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestBody @Valid UserCheckNicknameDto dto) {
        Boolean check = userService.checkUserNickname(dto);

        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

}