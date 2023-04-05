package com.tomato.gateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义处理token请求头鉴权失败的结果
 *
 * @author lizhifu
 * @since 2023/4/5
 */
@Slf4j
public class CustomServerAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        log.error("CustomServerAccessDeniedHandler exchange:{},denied:{}",exchange,denied);
        return null;
    }
}
