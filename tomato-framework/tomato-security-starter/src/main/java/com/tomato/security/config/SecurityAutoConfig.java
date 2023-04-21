package com.tomato.security.config;

import com.tomato.security.token.TokenService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
     *
     * @see <a href="http://stackabuse.com/password-encoding-with-spring-security/">Password Encoding with Spring Security</a>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public TokenService tokenService(StringRedisTemplate stringRedisTemplate){
        return new TokenService(stringRedisTemplate);
    }
}
