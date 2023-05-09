package com.tomato.auth.config;

import com.tomato.auth.handler.CustomAuthenticationHandler;
import com.tomato.auth.properties.AuthProperties;
import com.tomato.auth.user.impl.CustomUserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.config.Customizer.withDefaults;


/**
 * 默认安全配置
 * <p>
 * 默认异常处理：{@link Http403ForbiddenEntryPoint}
 * @author lizhifu
 * @since 2023/5/3
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@Slf4j
public class DefaultSecurityConfig {
    /**
     * 默认忽略的静态资源
     */
    private static final List<String> DEFAULT_IGNORED_STATIC_RESOURCES = List.of(
            "/assets/**", "/webjars/**", "/login");
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity, AuthProperties authProperties) throws Exception {
        log.info("默认安全配置");
        // 禁用CSRF 开启跨域
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);
        httpSecurity
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // 忽略静态资源
                        .requestMatchers(DEFAULT_IGNORED_STATIC_RESOURCES.toArray(new String[0])).permitAll()
                        // 其他请求需要认证
                        .anyRequest().authenticated()
                )
                // 表单登录
                .formLogin(form->{
                    // 登录配置
                    form.loginPage(authProperties.getLoginPageUrl())
                            .usernameParameter(authProperties.getUsernameParameter())
                            .passwordParameter(authProperties.getPasswordParameter());
                    // successForwardUrl：请求转发，转发后浏览器的地址不会变，登录成功后不会跳转到原来的地址
                    // defaultSuccessUrl：302重定向，登录成功后会跳转到原来的地址
                    Optional.ofNullable(authProperties.getSuccessForwardUrl())
                            .ifPresent(form::successForwardUrl);
                    Optional.ofNullable(authProperties.getFailureForwardUrl())
                            .ifPresent(form::failureForwardUrl);
                    form.permitAll();
                });
        return httpSecurity.build();
    }

    /**
     * OAuth2 中的资源拥有者（Resource Owner）。资源拥有者是指能够对资源进行授权的主体，通常是用户。
     * @return UserDetailsService
     */
    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService() {
        log.info("自定义 UserDetailsService 配置");
        return new CustomUserDetailsServiceImpl();
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * 密码加密
     * @return 密码加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
