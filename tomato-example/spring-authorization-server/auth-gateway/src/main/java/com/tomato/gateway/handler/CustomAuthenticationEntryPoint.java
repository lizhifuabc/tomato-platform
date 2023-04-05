package com.tomato.gateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义处理token请求头过期或签名错误的结果
 *
 * @author lizhifu
 * @since 2023/4/5
 */
@Slf4j
public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        log.error("CustomAuthenticationEntryPoint exchange:{},ex:{}",exchange,ex);
        return null;
    }
}
