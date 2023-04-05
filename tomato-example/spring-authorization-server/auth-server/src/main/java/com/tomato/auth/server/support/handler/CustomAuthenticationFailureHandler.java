package com.tomato.auth.server.support.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * 登录失败处理器
 *
 * @author lizhifu
 * @since 2023/4/5
 */
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        String user = request.getParameter(OAuth2ParameterNames.CODE);
        log.info("用户：{} 登录失败，异常：{}", user, exception.getLocalizedMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("登录失败");
    }
}
