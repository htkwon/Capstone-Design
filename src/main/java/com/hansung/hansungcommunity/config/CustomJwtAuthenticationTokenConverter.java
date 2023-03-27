package com.hansung.hansungcommunity.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomJwtAuthenticationTokenConverter implements Converter<Jwt , JwtAuthenticationToken> {
    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        List<String> authorities = (List<String>) jwt.getClaims().get("authorities");

        if(!(authorities.contains("read") || authorities.contains("write"))){
            throw new InvalidBearerTokenException("토큰이 올바른 scope를 가지고 있지 않습니다.");
        }

        return new JwtAuthenticationToken(jwt, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
