package com.tomato.gateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

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
        log.error("token请求头过期或签名错误",ex);
        return Mono.defer(() -> Mono.just(exchange.getResponse())).flatMap(response -> {

            response.setStatusCode(HttpStatus.UNAUTHORIZED);

            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            DataBufferFactory dataBufferFactory = response.bufferFactory();

            String result = "token请求头过期或签名错误";

            DataBuffer buffer = dataBufferFactory.wrap(result.getBytes(Charset.defaultCharset()));

            return response.writeWith(Mono.just(buffer));
        });
    }
}
