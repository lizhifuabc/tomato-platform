package com.tomato.oauth2.authentication.config;

import com.tomato.oauth2.core.response.CustomAccessDeniedHandler;
import com.tomato.oauth2.core.response.CustomAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 默认安全配置
 *
 * @author lizhifu
 * @since 2023/9/2
 */
@EnableWebSecurity
@Slf4j
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		log.info("[Oauth2] | 默认安全配置");
		// 禁用CSRF 开启跨域
		httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);

		httpSecurity
				// 异常处理
				.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
					httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
					httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(new CustomAccessDeniedHandler());
				});
		// build() 配置生效
		return httpSecurity.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		log.info("[Oauth2] | 密码编码器");
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
