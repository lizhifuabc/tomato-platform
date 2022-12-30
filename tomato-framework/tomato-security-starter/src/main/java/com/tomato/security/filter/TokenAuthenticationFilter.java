package com.tomato.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.function.BiFunction;

import static com.tomato.security.constant.RequestHeaderConstant.AUTHORIZATION_BEARER;
import static com.tomato.security.constant.RequestHeaderConstant.TOKEN;

/**
 * 不能加 @Component，否则对应ignoreUrl的相关请求 将会进入此Filter，并会覆盖CorsFilter
 * <p>Token 过滤器，验证 token 的有效性</p>
 * <p>验证通过后，获得 LoginUser 信息，并加入到 Spring Security 上下文</p>
 * @author lizhifu
 * @since 2022/12/16
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final BiFunction<String,HttpServletRequest, UserDetails> userFunction;
    public TokenAuthenticationFilter(BiFunction<String, HttpServletRequest, UserDetails> userFunction) {
        this.userFunction = userFunction;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 消息头 token
        String token = getToken(request);
        if(ObjectUtils.isEmpty(token)){
            chain.doFilter(request, response);
            return;
        }
        // 清理 spring security，
        // 若未给予spring security 上下文用户授权，授权失败 AuthenticationEntryPointImpl
        //  SecurityContextHolder.clearContext(); TODO
        // TODO
        UserDetails userDetails = userFunction.apply(token,request);
        if(null != userDetails){
            // 创建 Authentication，并设置到上下文
            // 创建 UsernamePasswordAuthenticationToken 对象
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // TODO 额外设置到 request 中，用于 ApiAccessLogFilter 可以获取到用户编号；
            // 原因是，Spring Security 的 Filter 在 ApiAccessLogFilter 后面，在它记录访问日志时，线上上下文已经没有用户编号等信息
        }
        // 若未给予spring security 上下文用户授权，授权失败 AuthenticationEntryPointImpl
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
        int index = authorization.indexOf(AUTHORIZATION_BEARER);
        // 未找到
        if (index == -1) {
            return null;
        }
        return authorization.substring(index + 7).trim();
    }
}
