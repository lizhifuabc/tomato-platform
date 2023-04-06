package com.tomato.auth.server.support;

import com.tomato.auth.server.support.handler.CustomFormAuthenticationFailureHandler;
import com.tomato.auth.server.support.handler.SsoLogoutSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * 基于授权码模式 统一认证登录 spring security & saas 都可以使用 所以抽取成 HttpConfigurer
 *
 * @author lizhifu
 * @since 2023/4/6
 */
@Slf4j
public final class FormIdentityLoginConfigurer extends AbstractHttpConfigurer<FormIdentityLoginConfigurer, HttpSecurity> {
    @Override
    public void init(HttpSecurity http) throws Exception {
        log.info("FormIdentityLoginConfigurer init");
        http.formLogin();
        http.formLogin(formLogin -> {
                    formLogin.loginPage("/token/login");
                    formLogin.loginProcessingUrl("/token/form");
                    formLogin.failureHandler(new CustomFormAuthenticationFailureHandler());

                }).logout() // SSO登出成功处理
                .logoutSuccessHandler(new SsoLogoutSuccessHandler()).deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).and().csrf().disable();
    }
}
