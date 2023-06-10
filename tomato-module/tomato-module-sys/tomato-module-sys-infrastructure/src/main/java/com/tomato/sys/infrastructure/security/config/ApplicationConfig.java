package com.tomato.sys.infrastructure.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 应用配置
 *
 * @author lizhifu
 * @since 2023/6/8
 */
@Configuration
@Slf4j
public class ApplicationConfig {
    private final UserDetailsService userDetailsService;
    private final UserDetailsPasswordService userDetailsPasswordSerivce;
    public ApplicationConfig(UserDetailsService userDetailsService, UserDetailsPasswordService userDetailsPasswordSerivce) {
        this.userDetailsService = userDetailsService;
        this.userDetailsPasswordSerivce = userDetailsPasswordSerivce;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("密码加密方式配置，passwordEncoder");
        //return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return  userDetailsService;
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        // 配置密码自动升级服务
        authProvider.setUserDetailsPasswordService(userDetailsPasswordSerivce);
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
