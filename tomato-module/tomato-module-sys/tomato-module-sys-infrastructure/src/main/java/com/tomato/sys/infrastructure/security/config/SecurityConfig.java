package com.tomato.sys.infrastructure.security.config;

import com.tomato.security.config.AbstractSecurityConfig;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.function.BiFunction;

/**
 * 权限
 *
 * @author lizhifu
 * @since 2022/12/17
 */
@Slf4j
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig extends AbstractSecurityConfig {
    @Resource
    private UserDetailsService userDetailsService;
    @Override
    protected BiFunction<String, HttpServletRequest, UserDetails> userFunction() {
        // return (token, request) -> userDetailsService.loadUserByUsername(token,request);
        return (token, request) -> userDetailsService.loadUserByUsername(token);
    }
}
