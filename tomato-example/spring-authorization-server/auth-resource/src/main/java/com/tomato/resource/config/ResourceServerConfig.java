package com.tomato.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 资源服务器配置
 * @author lizhifu
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class ResourceServerConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/hello/**")
				.authorizeHttpRequests()
					.requestMatchers("/hello/**").hasAuthority("SCOPE_message.read")
					.and()
			.oauth2ResourceServer()
				.jwt();
		return http.build();
	}
}
