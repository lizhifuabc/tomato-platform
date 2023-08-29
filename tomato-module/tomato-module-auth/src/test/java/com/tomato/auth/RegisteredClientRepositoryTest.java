package com.tomato.auth;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.util.UUID;

/**
 * RegisteredClientRepository
 *
 * @author lizhifu
 * @since 2023/4/5
 */
@SpringBootTest
public class RegisteredClientRepositoryTest {

	@Resource
	private JdbcRegisteredClientRepository registeredClientRepository;

	@Test
	public void test() {
		RegisteredClient registeredClient1 = RegisteredClient.withId(UUID.randomUUID().toString())
			// 客户端ID和密码
			.clientId("messaging-client1")
			// client_secret_basic 客户端需要存明文 服务器存密文
			.clientSecret("{noop}secret1")
			// 名称 可不定义
			.clientName("客户端名称")
			// 授权方法
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
			// 授权类型
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
			.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
			// 回调地址名单，不在此列将被拒绝 而且只能使用IP或者域名 不能使用 localhost
			.redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
			.redirectUri("http://127.0.0.1:8080/authorized")
			.postLogoutRedirectUri("http://127.0.0.1:8080/index")
			// OIDC支持
			.scope(OidcScopes.OPENID)
			.scope(OidcScopes.PROFILE)
			// 其它Scope
			.scope("message.read")
			.scope("message.write")
			// JWT的配置项 包括TTL 是否复用refreshToken等等
			.tokenSettings(TokenSettings.builder().build())
			// 配置客户端相关的配置项，包括验证密钥或者 是否需要授权页面
			.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
			.build();

		RegisteredClient registeredClient2 = RegisteredClient.withId(UUID.randomUUID().toString())
			.clientId("messaging-client2")
			.clientSecret("{noop}secret2")
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
			.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
			.redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
			.redirectUri("http://127.0.0.1:8080/authorized")
			.postLogoutRedirectUri("http://127.0.0.1:8080/logged-out")
			.scope(OidcScopes.OPENID)
			.scope(OidcScopes.PROFILE)
			.scope("message.read")
			.scope("message.write")
			.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
			.build();

		RegisteredClient deviceClient = RegisteredClient.withId(UUID.randomUUID().toString())
			.clientId("device-messaging-client")
			.clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
			.authorizationGrantType(AuthorizationGrantType.DEVICE_CODE)
			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
			.scope("message.read")
			.scope("message.write")
			.build();

		registeredClientRepository.save(registeredClient1);
		registeredClientRepository.save(registeredClient2);
		registeredClientRepository.save(deviceClient);
	}

}
