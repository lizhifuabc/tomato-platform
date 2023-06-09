package com.tomato.sys.infrastructure.security.config;

import com.tomato.sys.domain.constants.RequestHeaderConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * 登出
 *
 * @author lizhifu
 * @since 2023/6/8
 */
@Service
public class LogoutService implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(RequestHeaderConstant.TOKEN);
        if (authHeader == null ||!authHeader.startsWith(RequestHeaderConstant.AUTHORIZATION_BEARER)) {
            return;
        }
        final String jwt;
    }
}
