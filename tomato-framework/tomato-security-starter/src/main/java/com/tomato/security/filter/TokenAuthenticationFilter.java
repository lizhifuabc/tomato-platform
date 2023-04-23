package com.tomato.security.filter;

import com.tomato.security.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiFunction;

import static com.tomato.security.constant.RequestHeaderConstant.AUTHORIZATION_BEARER;
import static com.tomato.security.constant.RequestHeaderConstant.TOKEN;

/**
 * 每次请求的 Security 过滤类。执行jwt有效性检查，
 * 如果失败，不会设置 SecurityContextHolder 信息，会进入 AuthenticationEntryPoint
 * <p>验证通过后，获得 LoginUser 信息，并加入到 Spring Security 上下文</p>
 * @author lizhifu
 * @since 2022/12/16
 */
@Slf4j
@Component
@Deprecated
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final BiFunction<String,HttpServletRequest, UserDetails> userFunction;
    private final TokenService tokenService;
    public TokenAuthenticationFilter(BiFunction<String, HttpServletRequest, UserDetails> userFunction, TokenService tokenService) {
        this.userFunction = userFunction;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 消息头 token
        String token = getToken(request);
        log.info("token校验:{}",token);
        if(ObjectUtils.isEmpty(token)){
            chain.doFilter(request, response);
            return;
        }
        Map<String, Object> decryptedToken = tokenService.decryptToken(token);
        log.info("token解密:{}",decryptedToken);
        boolean checked = tokenService.checkRedisToken(decryptedToken, token);
        log.info("token校验结果:{}",checked);
        if(!checked){
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            chain.doFilter(request, response);
            return;
        }
        // 清理 spring security，
        // 若未给予spring security 上下文用户授权，授权失败 AuthenticationEntryPointImpl
        //  SecurityContextHolder.clearContext(); TODO
        UserDetails userDetails = userFunction.apply(token,request);
        if(null != userDetails){
            // 创建 Authentication，并设置到上下文
            // 创建 UsernamePasswordAuthenticationToken 对象
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            // 后续的过滤器中，可以通过 WebAuthenticationDetailsSource 获取到用户的 IP 等信息
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