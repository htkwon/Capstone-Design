package com.hansung.hansungcommunity.config;

import com.hansung.hansungcommunity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Value("${jwksUri}")
    private String jwksUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
                r -> r.jwt().jwkSetUri(jwksUri) // get Public key from Authorization Server
                        .jwtAuthenticationConverter(new CustomJwtAuthenticationTokenConverter(userService))
        );

        http.authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/join").permitAll()
                .antMatchers("/api/check").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}
