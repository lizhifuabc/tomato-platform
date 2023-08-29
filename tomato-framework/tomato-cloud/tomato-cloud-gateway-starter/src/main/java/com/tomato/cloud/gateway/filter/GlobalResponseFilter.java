package com.tomato.cloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 统一返回的响应数据格式 TODO implements GlobalFilter, Ordered
 *
 * @author lizhifu
 * @since 2023/7/19
 */
public class GlobalResponseFilter {

}
