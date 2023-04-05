package com.tomato.auth.server.support.constant;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * 常用常量
 *
 * @author lizhifu
 * @since 2023/4/5
 */
public interface SecurityConstants {
    /**
     * {bcrypt} 加密的特征码 TODO 未使用
     */
    String BCRYPT = "{bcrypt}";
    /**
     * 手机号登录
     */
    AuthorizationGrantType SMS = new AuthorizationGrantType("sms");

    /**
     * access_token+google_token
     */
    AuthorizationGrantType GOOGLE = new AuthorizationGrantType("google");
}
