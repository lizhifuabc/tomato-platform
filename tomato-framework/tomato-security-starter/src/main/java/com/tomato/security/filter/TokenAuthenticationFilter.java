package com.tomato.security.filter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 此处不能 加入@Component，否则对应ignoreUrl的相关请求 将会进入此Filter，并会覆盖CorsFilter
 * <p>Token 过滤器，验证 token 的有效性</p>
 * <p>验证通过后，获得 LoginUser 信息，并加入到 Spring Security 上下文</p>
 * @author lizhifu
 * @since 2022/12/16
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    public static final String TOKEN = "x-access-token";

    public static final String USER_AGENT = "user-agent";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(TOKEN);
        String xRequestToken = request.getParameter(TOKEN);
        String xAccessToken = null != xHeaderToken ? xHeaderToken : xRequestToken;
        if (StringUtils.isBlank(xAccessToken)) {
            chain.doFilter(request, response);
            return;
        }
        //清理spring security
        SecurityContextHolder.clearContext();

//        UserDetails loginUserDetail = userFunction.apply(xAccessToken,request);
//        if (null != loginUserDetail) {
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDetail, null, loginUserDetail.getAuthorities());
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            SmartRequestUtil.setRequestUser((RequestUser) loginUserDetail);
//        }
        // 若未给予spring security上下文用户授权 则会授权失败 进入AuthenticationEntryPointImpl
        chain.doFilter(request, response);
    }
}
