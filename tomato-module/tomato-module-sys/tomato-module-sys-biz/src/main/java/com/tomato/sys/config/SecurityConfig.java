package com.tomato.sys.config;

import com.tomato.security.config.AbstractSecurityConfig;
import com.tomato.sys.login.service.LoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;

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
    private LoginService loginService;
    @Override
    protected BiFunction<String, HttpServletRequest, UserDetails> userFunction() {
        return (token, request) -> loginService.getLoginUserDetail(token, request);
    }
}
