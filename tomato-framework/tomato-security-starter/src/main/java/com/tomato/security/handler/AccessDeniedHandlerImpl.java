package com.tomato.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.common.resp.Resp;
import com.tomato.security.util.SecurityFrameworkUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 权限不够处理器->认证通过，访问没有权限的 URI
 *
 * @author lizhifu
 * @since 2022/12/16
 */
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 打印 warn 的原因是，不定期合并 warn，看看有没恶意破坏
        log.warn("[commence][访问 URL({}) 时，用户({}) 权限不够]", request.getRequestURI(),
                SecurityFrameworkUtil.getLoginUser(), accessDeniedException);
        // 请求被禁止、超出访问权限。与401不同，请求已经通过了身份验证，只是没有获得资源授权
        Resp resp = Resp.buildFailure(String.valueOf(HttpStatus.FORBIDDEN.value()),"未登录访问");
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(resp));
        response.flushBuffer();
    }
}
