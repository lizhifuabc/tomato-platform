package com.tomato.security.config;

import com.tomato.security.filter.TokenAuthenticationFilter;
import com.tomato.security.handler.AccessDeniedHandlerImpl;
import com.tomato.security.handler.AuthenticationEntryPointImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import java.util.function.BiFunction;

/**
 * Spring Security 配置
 *
 * @author lizhifu
 * @since 2022/12/16
 */
public abstract class AbstractSecurityConfig {
    @Resource
    private CorsFilter defaultCorsFilter;
    /**
     * Token获取用户信息
     *
     * @return
     */
    protected abstract BiFunction<String, HttpServletRequest, UserDetails> userFunction();
    @Resource
    private SecurityProperties securityProperties;
    /**
     * 配置 URL 的安全配置
     * <p>
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // 登出
        httpSecurity
                // 开启跨域，不能同时开启跨域配置
//                .cors().and()
                // CSRF禁用，不使用session
                .csrf().disable()
                // 基于 token 机制，所以不需要 Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .headers().frameOptions().disable().and()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPointImpl())
                // 权限不够处理器
                .accessDeniedHandler(new AccessDeniedHandlerImpl());

        // 设置请求权限
        httpSecurity.authorizeHttpRequests()
                // 静态资源，可匿名访问
                .requestMatchers(HttpMethod.GET, "/*.html", "/*/*.html", "/*/*.css", "/*/*.js").permitAll()
                // 免登录的 URL 列表
                .requestMatchers(securityProperties.getPermitAllUrls().toArray(new String[0])).permitAll()
                // ②：每个项目的自定义规则
                .and()
                // 其他必须认证
                .authorizeHttpRequests()
                .anyRequest().authenticated()
        ;
        // 添加 Token Filter
        httpSecurity.addFilterBefore(new TokenAuthenticationFilter(this.userFunction()), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(defaultCorsFilter, TokenAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
