package com.tomato.sys.infrastructure.security.config;

import com.tomato.common.resp.Resp;
import com.tomato.jackson.utils.JacksonUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

/**
 * 权限
 *
 * @author lizhifu
 * @since 2022/12/17
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final String LOG_OUT_URL = "/sys/user/logout";
    private final String[] permitAll = new String[]{
            "/sys/user/login",
            LOG_OUT_URL,
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("安全配置，securityFilterChain");
        http
                // 关闭csrf,允许跨域
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers(permitAll).permitAll();
                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(out -> out
                        .logoutUrl(LOG_OUT_URL)
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .authenticationEntryPoint((request, response, authException) -> {
                            exceptionHandling(response,Resp.buildFailure("401","未授权"));
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            log.warn("权限不足", accessDeniedException);
                            exceptionHandling(response,Resp.buildFailure("403","权限不足"));
                        }))
        ;
        return http.build();
    }

    private static void exceptionHandling(HttpServletResponse response,Resp<Void> resp){
        try {
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().print(JacksonUtils.toJson(resp));
            response.getWriter().flush();
            response.getWriter().close();
        }catch (Exception e){
            log.error("exceptionHandling",e);
        }
    }
}
