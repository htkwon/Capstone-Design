package com.hansung.hansungcommunity.config;

import com.hansung.hansungcommunity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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

        http.csrf().disable();

        http.oauth2ResourceServer(
                r -> r.jwt().jwkSetUri(jwksUri) // get Public key from Authorization Server
                        .jwtAuthenticationConverter(new CustomJwtAuthenticationTokenConverter(userService))
        );

        http.authorizeHttpRequests()
                .antMatchers("/images/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/join").hasRole("STUDENT")
                .antMatchers(HttpMethod.GET, "/api/check").hasAnyRole("STUDENT", "USER")
                .antMatchers(HttpMethod.GET, "/api/freeBoards").permitAll()
                .antMatchers(HttpMethod.GET, "/api/qnaBoards").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}
