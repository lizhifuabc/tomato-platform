package com.tomato.gateway.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * 自定义全局过滤器
 *
 * @author lizhifu
 * @since 2023/5/3
 */
@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    private static final String MAX_AGE = "18000L";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 判断是否为跨域请求
        if (!CorsUtils.isCorsRequest(request)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse response = exchange.getResponse();
        if (request.getMethod() == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        HttpHeaders requestHeaders = request.getHeaders();
        HttpMethod method = requestHeaders.getAccessControlRequestMethod();
        HttpHeaders headers = response.getHeaders();
        // 服务器允许请求的源
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
        // 服务器允许使用的头
        headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
        Optional.ofNullable(method).ifPresent(requestMethod -> {
            // 服务器允许的请求方法
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
        });
        // 是否允许用户发送、处理 cookie
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        // 响应报头、跨域 公开响应头
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        // 预检请求的有效期，单位为秒。有效期内，不会重复发送预检请求；
        headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 值越小优先级越高
        return -1;
    }
}
