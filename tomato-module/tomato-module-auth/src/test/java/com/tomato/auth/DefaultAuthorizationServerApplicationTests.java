package com.tomato.auth;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * 授权服务器的集成测试。
 *
 * @author lizhifu
 * @since 2023/5/8
 */
public class DefaultAuthorizationServerApplicationTests {
    private static final String REDIRECT_URI = "http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc";
    /**
     * OAuth 2.0 的授权端点，用于获取授权码或访问令牌。
     */
    private static final String AUTHORIZATION_REQUEST = UriComponentsBuilder
            .fromPath("/oauth2/authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", "messaging-client")
            .queryParam("scope", "openid")
            .queryParam("state", "some-state")
            .queryParam("redirect_uri", REDIRECT_URI)
            .toUriString();

    public static void main(String[] args) {
        System.out.println(AUTHORIZATION_REQUEST);
    }
}
