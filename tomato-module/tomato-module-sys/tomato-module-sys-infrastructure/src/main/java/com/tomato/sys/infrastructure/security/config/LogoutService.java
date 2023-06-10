package com.tomato.sys.infrastructure.security.config;

import com.tomato.sys.domain.constants.RequestHeaderConstant;
import com.tomato.sys.infrastructure.repository.SysTokenRepository;
import com.tomato.sys.infrastructure.repository.SysUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * 登出
 *
 * @author lizhifu
 * @since 2023/6/8
 */
@Service
@Slf4j
public class LogoutService implements LogoutHandler {
    private final SysTokenRepository tokenRepository;
    private final JwtService jwtService;
    private final SysUserRepository sysUserRepository;
    public LogoutService(SysTokenRepository tokenRepository, JwtService jwtService, SysUserRepository sysUserRepository) {
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(RequestHeaderConstant.TOKEN);
        if (authHeader == null ||!authHeader.startsWith(RequestHeaderConstant.AUTHORIZATION_BEARER)) {
            return;
        }
        String username = jwtService.extractUsername(authHeader.substring(7));
        log.info("用户退出:{}",username);
        sysUserRepository.findByUsername(username).flatMap(tokenRepository::findBySysUser).ifPresent(t -> {
            t.setExpired(true);
            t.setRevoked(true);
            tokenRepository.save(t);
        });
        SecurityContextHolder.clearContext();
    }
}
