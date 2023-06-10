package com.tomato.sys.infrastructure.security.config;

import com.tomato.sys.domain.constants.RequestHeaderConstant;
import com.tomato.sys.infrastructure.repository.SysTokenRepository;
import com.tomato.sys.infrastructure.security.user.SecurityUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * jwt过滤器
 *
 * @author lizhifu
 * @since 2023/6/8
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final SysTokenRepository tokenRepository;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(RequestHeaderConstant.TOKEN);
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith(RequestHeaderConstant.AUTHORIZATION_BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);
        // 获取当前登录用户的Authentication对象 SecurityContextHolder.getContext().getAuthentication()
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityUserDetails userDetails = (SecurityUserDetails) userDetailsService.loadUserByUsername(username);

            boolean isTokenValid = tokenRepository.findBySysUser(userDetails.getSysUser())
                    .map(t -> !t.isExpired() && !t.isRevoked() && t.getToken().equals(jwt))
                    .orElse(false);
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                // 基于用户信息构建一个认证令牌对象
                // 该对象包含了用户信息以及用户权限等，和密码无关，可以保证安全性
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(userDetails);
                // 该authToken对象设置到SecurityContext中,表示该用户已认证登录
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
