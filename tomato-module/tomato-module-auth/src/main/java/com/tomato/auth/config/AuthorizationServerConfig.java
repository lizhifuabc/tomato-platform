package com.tomato.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.tomato.auth.jose.Jwks;
import com.tomato.auth.properties.AuthProperties;
import com.tomato.auth.properties.LocalJwkProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Optional;

/**
 * 认证授权服务器配置
 * <p>
 * 权限处理 {@link AuthorizationManager} 授权模式 {@link ProviderManager}
 *
 * @author lizhifu
 * @since 2023/5/3
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
public class AuthorizationServerConfig {

	/**
	 * 认证授权服务器过滤器链 OAuth2授权服务器专门处理OAuth2客户端的授权请求流程，授权端点、Token端点、用户信息端点等等都需要对应的过滤器支持。
	 * @param httpSecurity httpSecurity
	 * @param authProperties authProperties
	 * @return SecurityFilterChain
	 * @throws Exception Exception
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity,
			RegisteredClientRepository registeredClientRepository, AuthProperties authProperties,
			AuthorizationServerSettings authorizationServerSettings) throws Exception {
		log.info("认证服务器配置");
		// 对httpSecurity进行默认配置
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);

		httpSecurity.exceptionHandling(exceptions ->
		// 基于登录页面的认证方案入口，通过配置获取loginFormUrl（对应登录地址），未通过身份验证时重定向到登录页面
		// 对应UsernamePasswordAuthenticationFilter过滤器类。
		exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
			// 资源服务器配置
			.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));

		return httpSecurity.build();
	}

	/**
	 * 认证授权服务器配置
	 * @return AuthorizationServerSettings
	 */
	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

	/**
	 * JWK 源配置
	 * @param authProperties authProperties
	 * @return JWKSource
	 */
	@Bean
	public JWKSource<SecurityContext> jwkSource(AuthProperties authProperties) {
		Optional<LocalJwkProperties> localJwkProperties = Optional.ofNullable(authProperties.getLocalJwk());
		RSAKey rsaKey;
		if (localJwkProperties.isPresent()) {
			LocalJwkProperties jwkProperties = localJwkProperties.get();
			// 本地JWK
			rsaKey = Jwks.generateRsa(jwkProperties);
		}
		else {
			rsaKey = Jwks.generateRsa();
		}
		JWKSet jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	/**
	 * JWT解码器
	 * @param jwkSource JWK 源配置
	 * @return JwtDecoder
	 */
	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	/**
	 * OAuth2客户端信息需要持久化到数据库
	 * @param jdbcTemplate 数据源
	 * @return RegisteredClientRepository
	 */
	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
		return new JdbcRegisteredClientRepository(jdbcTemplate);
	}

	/**
	 * 授权状态信息持久化
	 * @param jdbcTemplate 数据源
	 * @param registeredClientRepository 注册客户端持久化
	 * @return OAuth2AuthorizationConsentService
	 */
	@Bean
	public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate,
			RegisteredClientRepository registeredClientRepository) {
		// AuthorizationConsentController
		return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
	}

	/**
	 * 授权状态信息持久化 资源拥有者的OAuth2授权状态信息 OAuth2Authorization 持久化管理
	 * @param jdbcTemplate 数据源
	 * @param registeredClientRepository 注册客户端持久化
	 * @return OAuth2AuthorizationService
	 */
	@Bean
	public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
	}

}
