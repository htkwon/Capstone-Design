package com.hansung.hansungcommunity.config;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomJwtAuthenticationTokenConverter implements Converter<Jwt , AbstractAuthenticationToken> {

    private final UserService userService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Optional<User> user = userService.getByStudentId(jwt.getSubject()); // 학번으로 유저 조회

        List<String> authorities = (List<String>) jwt.getClaims().get("authorities");

        // 유저가 있다면 커스텀한 Authentication 반환, ROLE_USER 권한 부여
        // 유저가 없는 경우 권한 부여 X
        if (user.isPresent()) {
            return new CustomAuthentication(user.get(), jwt, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        } else {
            return new JwtAuthenticationToken(jwt, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
    }

}
