package com.hansung.hansungcommunity.auth;

import com.hansung.hansungcommunity.entity.User;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

/**
 * User 를 포함하는 Custom Authentication
 */

@Getter
public class CustomAuthentication extends AbstractAuthenticationToken {

    private final User user;
    private final Jwt jwt;

    public CustomAuthentication(User user, Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        this.jwt = jwt;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return getJwt();
    }

    @Override
    public Object getPrincipal() {
        return getUser();
    }

    @Override
    public String getName() {
        return getJwt().getSubject();
    }

}
