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

	private final String LOG_OUT_URL = "/sys/user/auth/logout";

	private final String LOG_IN_URL = "/sys/user/auth/login";

	private final String[] permitAll = new String[] { LOG_IN_URL, LOG_OUT_URL, "/error/**",
			// actuator
			"/actuator/**", };

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
			.logout(out -> out.logoutUrl(LOG_OUT_URL)
				.addLogoutHandler(logoutHandler)
				.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
			.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
				.authenticationEntryPoint((request, response, authException) -> {
					exceptionHandling(response, Resp.buildFailure("401", "未授权:" + request.getRequestURI()));
				})
				.accessDeniedHandler((request, response, accessDeniedException) -> {
					log.warn("权限不足", accessDeniedException);
					exceptionHandling(response, Resp.buildFailure("403", "权限不足"));
				}));
		return http.build();
	}

	private static void exceptionHandling(HttpServletResponse response, Resp<Void> resp) {
		try {
			response.setContentType(MediaType.APPLICATION_JSON.toString());
			response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
			response.getWriter().print(JacksonUtils.toJson(resp));
			response.getWriter().flush();
			response.getWriter().close();
		}
		catch (Exception e) {
			log.error("exceptionHandling", e);
		}
	}

}
