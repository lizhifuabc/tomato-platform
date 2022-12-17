package com.tomato.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.function.BiFunction;

/**
 * 此处不能 加入@Component，否则对应ignoreUrl的相关请求 将会进入此Filter，并会覆盖CorsFilter
 * <p>Token 过滤器，验证 token 的有效性</p>
 * <p>验证通过后，获得 LoginUser 信息，并加入到 Spring Security 上下文</p>
 * @author lizhifu
 * @since 2022/12/16
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final BiFunction<String,HttpServletRequest, UserDetails> userFunction;
    public static final String TOKEN = "Authorization";
    private static final String AUTHORIZATION_BEARER = "Bearer";

    public TokenAuthenticationFilter(BiFunction<String, HttpServletRequest, UserDetails> userFunction) {
        this.userFunction = userFunction;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // token校验, 消息头token
        String token = getToken(request);
        // TODO
        userFunction.apply(token,request);
        if (StringUtils.hasText(token)) {

        }
        // 若未给予spring security上下文用户授权 则会授权失败 进入AuthenticationEntryPointImpl
        chain.doFilter(request, response);
    }
    /**
     * 从请求中，获得认证 Token
     *
     * @param request 请求
     * @return 认证 Token
     */
    private static String getToken(HttpServletRequest request) {
        String authorization = request.getHeader(TOKEN);
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        int index = authorization.indexOf(AUTHORIZATION_BEARER + " ");
        // 未找到
        if (index == -1) {
            return null;
        }
        return authorization.substring(index + 7).trim();
    }
}
