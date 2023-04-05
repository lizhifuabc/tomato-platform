package com.tomato.auth.server;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/4/5
 */
public class Test {
    private static final String REDIRECT_URI = "https://www.baidu.com";

    private static final String AUTHORIZATION_REQUEST = UriComponentsBuilder
            .fromPath("/oauth2/authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", "client")
            .queryParam("scope", "demo")
            .queryParam("state", "some-state")
            .queryParam("redirect_uri", REDIRECT_URI)
            .toUriString();
    public static void main(String[] args) {
        System.out.println("http://localhost:9001"+AUTHORIZATION_REQUEST);
        System.out.println("http://localhost:9000/auth2-server"+AUTHORIZATION_REQUEST);
    }
}
