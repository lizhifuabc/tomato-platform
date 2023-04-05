package com.tomato.auth.server.config;

import com.tomato.auth.server.support.CustomJwtTokenCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 认证服务器配置
 *
 * @author lizhifu
 * @since 2023/4/4
 */
@Configuration(proxyBeanMethods = false)//禁用代理,避免重复创建bean
public class AuthorizationServerConfiguration {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain serverSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        OAuth2AuthorizationServerConfigurer configurer = httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class);

        // 服务端点 /oauth2/authorize /oauth2/token /oauth2/token/revocation /oauth2/introspect 等等
        configurer.authorizationServerSettings(AuthorizationServerSettings.builder().build());
        httpSecurity.apply(configurer);

        // TODO 你可以根据需求对authorizationServerConfigurer进行一些个性化配置
        RequestMatcher endpointsMatcher = configurer.getEndpointsMatcher();

        // 授权码登录的登录页个性化
        DefaultSecurityFilterChain securityFilterChain = httpSecurity.securityMatcher(endpointsMatcher).build();
        return securityFilterChain;
    }
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
