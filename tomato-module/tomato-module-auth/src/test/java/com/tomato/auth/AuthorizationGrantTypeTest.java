package com.tomato.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 授权模式测试 {@link AuthorizationGrantType}
 *
 * @author lizhifu
 * @since 2023/5/9
 */
public class AuthorizationGrantTypeTest {

	private static String url = "http://localhost:9000";

	/**
	 * 客户端凭证模式验证: 根据 client 的 id 和密钥即可获取 token， 无需用户参与 这种模式比较合适消费 api
	 * 的后端服务，比如拉取一组用户信息等直接使用如下地址获取 Token 即可 grant_type：必选参数（固定值“client_credentials”）
	 * client_id：必选参数 client_secret：必选参数 scope：可选参数（表示授权范围）
	 */
	@Test
	public void client_credentials() {
		String AUTHORIZATION_REQUEST = UriComponentsBuilder.fromPath("/oauth2/token")
			.queryParam("grant_type", "client_credentials")
			.queryParam("client_id", "messaging-client1")
			.queryParam("client_secret", "secret1")
			.toUriString();
		System.out.println(AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
		System.out.println(url + AUTHORIZATION_REQUEST);
	}

}
