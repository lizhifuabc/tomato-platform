package com.tomato.auth.server.config;

import com.tomato.auth.server.service.CustomUserDetailService;
import com.tomato.auth.server.userdetails.CustomUserDetailsAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * <p>Spring Security 默认的安全策略</p>
 * @author lizhifu
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {
	private final Map<String, CustomUserDetailService> userDetailsServiceMap;

	public DefaultSecurityConfig(Map<String, CustomUserDetailService> userDetailsServiceMap) {
		this.userDetailsServiceMap = userDetailsServiceMap;
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		//其它任意接口都需认证
		httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
		// 处理 UsernamePasswordAuthenticationToken
		httpSecurity.authenticationProvider(new CustomUserDetailsAuthenticationProvider(userDetailsServiceMap));
		// 默认登录页面
		httpSecurity.formLogin(withDefaults());
		return httpSecurity.build();
	}
	@Bean
	SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

}
