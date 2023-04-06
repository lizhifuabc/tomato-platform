package com.tomato.auth.server;

import com.tomato.auth.server.support.constant.SecurityConstants;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Arrays;
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
    @Resource
    private PasswordEncoder passwordEncoder;

    private final String id = UUID.randomUUID().toString();
    private final String client = "client";
    private final String secret = "secret";
    @Test
    public void test() {
        RegisteredClient registeredClient = RegisteredClient.withId(id)
                /* 客户端ID和密码 */
                .clientId(client)
                .clientSecret(passwordEncoder.encode(secret))
                /* 授权方法 */
                .clientAuthenticationMethods(AuthenticationMethods ->
                        AuthenticationMethods.addAll(Arrays.asList(
                                        ClientAuthenticationMethod.CLIENT_SECRET_BASIC,
                                        ClientAuthenticationMethod.CLIENT_SECRET_POST
                                )
                        )
                )
                /* 授权类型 */
                .authorizationGrantTypes(authorizationGrantTypes ->
                        authorizationGrantTypes.addAll(Arrays.asList(
                                        // 授权码
                                        AuthorizationGrantType.AUTHORIZATION_CODE,
                                        // 刷新token
                                        AuthorizationGrantType.REFRESH_TOKEN,
                                        // 客户端模式
                                        AuthorizationGrantType.CLIENT_CREDENTIALS,
                                        // 密码模式
                                        AuthorizationGrantType.PASSWORD,
                                        //谷歌验证模式
                                        SecurityConstants.GOOGLE,
                                        //短信模式
                                        SecurityConstants.SMS
                                )
                        )
                )
                // 重定向url
                .redirectUris(redirectUris ->
                        redirectUris.add("https://www.baidu.com")
                )
                // 客户端申请的作用域，也可以理解这个客户端申请访问用户的哪些信息
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("message.read")
                .scope("message.write")

                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)//jwt需要透明令牌
                        // accessToken 的有效期
                        .accessTokenTimeToLive(Duration.ofHours(12L))
                        // refreshToken 的有效期
                        .refreshTokenTimeToLive(Duration.ofHours(12L))
                        // 是否可重用刷新令牌
                        .reuseRefreshTokens(true)
                        .build()
                )
                // 是否需要用户确认一下客户端需要获取用户的哪些权限
                // 比如：客户端需要获取用户的 用户信息、用户照片
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)
                        .build()
                )
                .build();

        registeredClientRepository.save(registeredClient);
    }
}
