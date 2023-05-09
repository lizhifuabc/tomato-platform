package com.tomato.auth;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * 授权服务器授权页面
 *
 * @author lizhifu
 * @since 2023/5/8
 */
public class DefaultAuthorizationServerConsentTests {
    private static final String redirectUri = "http://127.0.0.1/login/oauth2/code/messaging-client-oidc";
    /**
     * OAuth 2.0 的授权端点，用于获取授权码或访问令牌。
     */
    private static final String authorizationRequestUri = UriComponentsBuilder
            .fromPath("/oauth2/authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", "messaging-client1")
            .queryParam("scope", "openid message.read message.write")
            .queryParam("state", "state")
            .queryParam("redirect_uri", redirectUri)
            .toUriString();

    public static void main(String[] args) {
        System.out.println(authorizationRequestUri);
    }
}
