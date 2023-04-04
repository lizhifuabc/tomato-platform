package com.tomato.auth.server.config;

import com.tomato.auth.server.support.CustomJwtTokenCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * 认证服务器配置
 *
 * @author lizhifu
 * @since 2023/4/4
 */
@Configuration(proxyBeanMethods = false)//禁用代理,避免重复创建bean
public class AuthorizationServerConfiguration {
    /**
     * 令牌生成规则
     * @return OAuth2TokenGenerator
     */
    @Bean
    public OAuth2TokenGenerator<OAuth2Token> oAuth2TokenGenerator(JwtEncoder jwtEncoder) {
        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
        // JWT token 增强
        jwtGenerator.setJwtCustomizer(new CustomJwtTokenCustomizer());
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, new OAuth2RefreshTokenGenerator());
    }
}
