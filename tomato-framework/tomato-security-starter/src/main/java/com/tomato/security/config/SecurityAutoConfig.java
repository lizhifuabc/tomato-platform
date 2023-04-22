package com.tomato.security.config;

import com.tomato.security.token.TokenService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 自动配置类，主要用于相关组件的配置
 *
 * @author lizhifu
 * @since 2022/12/16
 */
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfig {
    /**
     * Spring Security 加密器 BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    /**
     * Token服务
     * @param stringRedisTemplate StringRedisTemplate
     * @return TokenService
     */
    @Bean
    public TokenService tokenService(StringRedisTemplate stringRedisTemplate){
        return new TokenService(stringRedisTemplate);
    }
    /**
     * 登录时需要调用AuthenticationManager.authenticate执行一次校验
     *
     * @param config AuthenticationConfiguration
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
