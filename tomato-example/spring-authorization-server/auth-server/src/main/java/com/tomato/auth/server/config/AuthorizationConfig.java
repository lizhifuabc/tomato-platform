package com.tomato.auth.server.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * 认证配置
 * {@link EnableMethodSecurity} 开启全局方法认证，启用JSR250注解支持，启用注解 {@link Secured} 支持，
 * 在Spring Security 6.0版本中将@Configuration注解从@EnableWebSecurity, @EnableMethodSecurity, @EnableGlobalMethodSecurity
 * 和 @EnableGlobalAuthentication 中移除，使用这些注解需手动添加 @Configuration 注解
 * {@link EnableWebSecurity} 注解有两个作用:
 * 1. 加载了WebSecurityConfiguration配置类, 配置安全认证策略。
 * 2. 加载了AuthenticationConfiguration, 配置了认证信息。
 *
 * @author lzf
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class AuthorizationConfig {

	/**
	 * 自定义用户确认授权页
	 */
	private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

	/**
	 * 配置端点的过滤器链,身份验证过滤器链
	 *
	 * @param http spring security核心配置类
	 * @return 过滤器链
	 * @throws Exception 抛出
	 */
	@Bean
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		// 配置默认的设置，忽略认证端点的csrf校验
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
				// 开启OpenID Connect 1.0协议相关端点
				.oidc(Customizer.withDefaults())
				// 设置自定义用户确认授权页
				.authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.consentPage(CUSTOM_CONSENT_PAGE_URI));
		http
				// 当未登录时访问认证端点时重定向至login页面
				.exceptionHandling((exceptions) -> exceptions
						.defaultAuthenticationEntryPointFor(
								new LoginUrlAuthenticationEntryPoint("/login"),
								new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
						)
				)
				// 处理使用access token访问用户信息端点和客户端注册端点
				.oauth2ResourceServer((resourceServer) -> resourceServer
						.jwt(Customizer.withDefaults()));

		return http.build();
	}

	/**
	 * 配置认证相关的过滤器链
	 *
	 * @param http spring security核心配置类
	 * @return 过滤器链
	 * @throws Exception 抛出
	 */
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize
						// 放行静态资源
						.requestMatchers("/assets/**", "/webjars/**", "/login").permitAll()
						.anyRequest().authenticated()
				)
				// 指定登录页面
				.formLogin(formLogin ->
						formLogin.loginPage("/login")
				);
		// 添加BearerTokenAuthenticationFilter，将认证服务当做一个资源服务，解析请求头中的token
		http.oauth2ResourceServer((resourceServer) -> resourceServer
				.jwt(Customizer.withDefaults()));

		return http.build();
	}

	/**
	 * 配置密码解析器，使用BCrypt的方式对密码进行加密和验证
	 *
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	/**
	 * 配置客户端Repository ,如果数据库已经存在客户端数据或不需要默认设置，则直接注入一个JdbcRegisteredClientRepository即可
	 * 这里为了测试，自动注入一个客户端"messaging-client"
	 * @param jdbcTemplate    db 数据源信息
	 * @return 基于数据库的repository
	 */
//	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
		return new JdbcRegisteredClientRepository(jdbcTemplate);
	}


	/**
	 * 配置客户端Repository
	 *
	 * @param jdbcTemplate    db 数据源信息
	 * @param passwordEncoder 密码解析器
	 * @return 基于数据库的repository
	 */
	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
		RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
				// 客户端id
				.clientId("messaging-client")
				// 客户端秘钥，使用密码解析器加密
				.clientSecret(passwordEncoder.encode("123456"))
				// 客户端认证方式，基于请求头的认证
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				// 配置资源服务器使用该客户端获取授权时支持的方式
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				// 授权码模式回调地址，oauth2.1已改为精准匹配，不能只设置域名，并且屏蔽了localhost，本机使用127.0.0.1访问
				.redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
				.redirectUri("https://www.baidu.com")
				// 该客户端的授权范围，OPENID与PROFILE是IdToken的scope，获取授权时请求OPENID的scope时认证服务会返回IdToken
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				// 自定scope
				.scope("message.read")
				.scope("message.write")
				// 客户端设置，设置用户需要确认授权
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.build();

		// 基于db存储客户端，还有一个基于内存的实现 InMemoryRegisteredClientRepository
		JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);

		// 初始化客户端
		RegisteredClient repositoryByClientId = registeredClientRepository.findByClientId(registeredClient.getClientId());
		if (repositoryByClientId == null) {
			registeredClientRepository.save(registeredClient);
		}
		// 设备码授权客户端
		RegisteredClient deviceClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("device-message-client")
				// 公共客户端
				.clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
				// 设备码授权
				.authorizationGrantType(AuthorizationGrantType.DEVICE_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				// 自定scope
				.scope("message.read")
				.scope("message.write")
				.build();
		RegisteredClient byClientId = registeredClientRepository.findByClientId(deviceClient.getClientId());
		if (byClientId == null) {
			registeredClientRepository.save(deviceClient);
		}
		return registeredClientRepository;
	}

	/**
	 * 配置基于db的oauth2的授权管理服务。授权管理服务
	 *
	 * @param jdbcTemplate               db数据源信息
	 * @param registeredClientRepository 上边注入的客户端repository
	 * @return JdbcOAuth2AuthorizationService
	 */
	@Bean
	public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
		// 基于db的oauth2认证服务，还有一个基于内存的服务实现InMemoryOAuth2AuthorizationService
		return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
	}

	/**
	 * 配置基于db的授权确认管理服务，授权确认管理服务
	 *
	 * @param jdbcTemplate               db数据源信息
	 * @param registeredClientRepository 客户端repository
	 * @return JdbcOAuth2AuthorizationConsentService
	 */
	@Bean
	public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
		// 基于db的授权确认管理服务，还有一个基于内存的服务实现InMemoryOAuth2AuthorizationConsentService
		return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
	}

	/**
	 * 配置jwk源，使用非对称加密，公开用于检索匹配指定选择器的JWK的方法
	 *
	 * @return JWKSource
	 */
	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	/**
	 * 生成rsa密钥对，提供给jwk
	 *
	 * @return 密钥对
	 */
	private static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

	/**
	 * 配置jwt解析器
	 *
	 * @param jwkSource jwk源
	 * @return JwtDecoder
	 */
	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	/**
	 * 添加认证服务器配置，设置jwt签发者、默认端点请求地址等，配置认证服务器设置
	 *
	 * @return AuthorizationServerSettings
	 */
	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

	/**
	 * 先暂时配置一个基于内存的用户，框架在用户认证时会默认调用
	 * {@link UserDetailsService#loadUserByUsername(String)} 方法根据
	 * 账号查询用户信息，一般是重写该方法实现自己的逻辑
	 *
	 * @param passwordEncoder 密码解析器
	 * @return UserDetailsService
	 */
	@Bean
	public UserDetailsService users(PasswordEncoder passwordEncoder) {
		UserDetails user = User.withUsername("admin")
				// 密码加密
				.password(passwordEncoder.encode("123456"))
				// 设置用户角色
				.roles("admin", "normal", "unAuthentication")
				// 设置用户权限
				.authorities("app", "web", "/test2", "/test3")
				.build();
		return new InMemoryUserDetailsManager(user);
	}

}
