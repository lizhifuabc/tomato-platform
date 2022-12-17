package com.tomato.security.config;

import com.tomato.security.token.TokenService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Spring Security 自动配置类，主要用于相关组件的配置
 *
 * @author lizhifu
 * @since 2022/12/16
 */
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfig {
    @Bean
    public TokenService tokenService(){
        return new TokenService();
    }
}
