package com.tomato.auth.server.support;

import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * JWT token 增强
 *
 * @author lizhifu
 * @since 2023/4/4
 */
public class CustomJwtTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
    @Override
    public void customize(JwtEncodingContext context) {
        //TODO
    }
}
