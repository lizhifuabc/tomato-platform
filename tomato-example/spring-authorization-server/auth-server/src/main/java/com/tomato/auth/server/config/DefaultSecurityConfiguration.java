package com.tomato.auth.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 服务安全相关配置
 *
 * @author lizhifu
 * @since 2023/4/4
 */
@Configuration
@EnableWebSecurity
public class DefaultSecurityConfiguration {
    /**
     * 授权服务器的过滤器链
     * @param http Security注入点
     * @return SecurityFilterChain 安全过滤器链
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //开放自定义的部分端点
        // http.authorizeHttpRequests().requestMatchers("/token/*").permitAll();
        //其它任意接口都需认证
        http.authorizeHttpRequests().anyRequest().authenticated();
        // 允许iframe嵌入，避免iframe同源无法登录 & 表单登录个性化
        http.headers().frameOptions().sameOrigin();
        //.and().apply(new FormIdentityLoginConfigurer());
        // 处理 UsernamePasswordAuthenticationToken
        // http.authenticationProvider(new UserDetailsAuthenticationProvider());
        return http.build();
    }

    /**
     * 暴露静态资源
     * <a href="https://github.com/spring-projects/spring-security/issues/10938">...</a>
     * @param httpSecurity Security注入点
     * @return SecurityFilterChain 安全过滤器链
     * @throws Exception 异常
     */
    @Bean
    @Order(0)
    public SecurityFilterChain resources(HttpSecurity httpSecurity) throws Exception {
        // 开放端点:spring actuator 监控等等
        httpSecurity.authorizeHttpRequests().requestMatchers("/actuator/**", "/css/**", "/error").permitAll();
        // 其它任意接口都需认证
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        // 禁用部分不要的功能
        // requestCache: 禁用请求缓存
        // securityContext: 禁用SecurityContext
        // sessionManagement: 禁用session管理
        httpSecurity.requestCache().disable().securityContext().disable().sessionManagement().disable();
        return httpSecurity.build();
    }
}
