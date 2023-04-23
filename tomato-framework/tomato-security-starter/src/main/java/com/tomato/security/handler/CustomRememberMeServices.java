package com.tomato.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * 记住我服务
 * 从request的Attribute中获取rememberMe字段
 * 当字段值为TRUE_VALUES表的成员时认为需要开启记住我功能
 * <p>
 *     1.自定义RememberMeServices
 *     2.自定义RememberMeAuthenticationFilter
 *     3.自定义RememberMeAuthenticationProvider
 *     4.自定义RememberMeAuthenticationToken
 *     5.自定义RememberMeAuthenticationTokenFilter
 *     6.自定义RememberMeAuthenticationTokenProvider
 *     7.自定义RememberMeAuthenticationTokenService
 *     8.自定义RememberMeAuthenticationTokenServiceImpl
 *     9.自定义RememberMeAuthenticationTokenUserDetailsService
 *     10.自定义RememberMeAuthenticationTokenUserDetailsServiceImpl
 *     11.自定义RememberMeAuthenticationTokenUserDetails
 *     12.自定义RememberMeAuthenticationTokenUserDetailsImpl
 *     13.自定义RememberMeAuthenticationTokenUserDetailsMapper
 *     14.自定义RememberMeAuthenticationTokenUserDetailsMapperImpl
 *     15.自定义RememberMeAuthenticationTokenUserDetailsRepository
 *     16.自定义RememberMeAuthenticationTokenUserDetailsRepositoryImpl
 * @author lizhifu
 * @since 2023/4/23
 */
@Component
public class CustomRememberMeServices extends PersistentTokenBasedRememberMeServices {
    public static final String REMEMBER_ME_KEY = "rememberMe";
    public static final List<String> TRUE_VALUES = List.of("true", "yes", "on", "1");
    public CustomRememberMeServices(UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(UUID.randomUUID().toString(), userDetailsService, tokenRepository);
    }
    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        final String rememberMe = (String) request.getAttribute(REMEMBER_ME_KEY);
        if (rememberMe != null) {
            for (String trueValue : TRUE_VALUES) {
                if (trueValue.equalsIgnoreCase(rememberMe)) {
                    return true;
                }
            }
        }
        return super.rememberMeRequested(request, parameter);
    }
}
