package com.tomato.auth.server;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * TODO
 * 1. REDIRECT_URI,使用错误的地址
 * 2. scope 为demo,数据库为demo
 * 3. scope 为openid,数据库为demo
 * 4. scope 为openid,数据库为openid
 * 5. scope 为空
 * TODO 1 lb方式调用授权服务，需要配置负载均衡 2 scope权限获取不到
 *
 * @author lizhifu
 * @since 2023/4/5
 */
public class Test {

    private static final String REDIRECT_URI = "http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc";

    private static final String AUTHORIZATION_REQUEST = UriComponentsBuilder
            .fromPath("/oauth2/authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", "messaging-client")
            .queryParam("scope", "openid message.read message.write")
            .queryParam("state", "state")
            .queryParam("redirect_uri", REDIRECT_URI)
            .toUriString();

    public static void main(String[] args) {
        System.out.println("http://localhost:9000"+AUTHORIZATION_REQUEST);
    }
}
