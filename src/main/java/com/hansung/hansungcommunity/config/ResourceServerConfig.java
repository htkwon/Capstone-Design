package com.hansung.hansungcommunity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${jwksUri}")
    private String jwksUri;

    //TODO : Cors 설정
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //.antMatchers("/").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/api/**").permitAll() //TODO: 롤 스코프 설정(논의)
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    //Token Intropection (엑세스 토큰의 유효성 확인)
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8081/oauth/check_token");
        tokenServices.setClientId("resource_server");
        tokenServices.setClientSecret("resource_server_secret");
        resources.tokenServices(tokenServices);
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        //서명키 랜덤 생성
        //String signaturekey = SignatureKeyGenerator.generateSignatureKey();

        converter.setSigningKey(jwksUri);
        converter.setAccessTokenConverter(new DefaultAccessTokenConverter(){
            //JWT 토큰에 반환된 authorities 클레임값 검증
            @Override
            public OAuth2Authentication extractAuthentication(Map<String,?> claims){
                OAuth2Authentication auth = super.extractAuthentication(claims);

                if(claims.containsKey("authorities") && !(claims.get("authorities") instanceof Collection)){
                    Collection<String> authorities = Arrays.asList(claims.get("authorities").toString().split(" "));
                    if(!(authorities.contains("read") || authorities.contains("write"))){
                        throw new InvalidTokenException("토큰이 옳바른 scope를 가지고 있지 않습니다.");
                    }
                }
                return auth;
            }

        });
        return converter;
    }

}
