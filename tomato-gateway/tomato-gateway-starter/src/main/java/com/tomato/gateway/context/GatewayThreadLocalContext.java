package com.tomato.gateway.context;

import org.springframework.web.server.ServerWebExchange;

/**
 * 网关线程上下文
 *
 * @author lizhifu
 * @since 2023/7/18
 */
public class GatewayThreadLocalContext {
    private ServerWebExchange exchange;
    private final static ThreadLocal<GatewayThreadLocalContext> THREAD_LOCAL = new ThreadLocal<GatewayThreadLocalContext>() {
        @Override
        protected GatewayThreadLocalContext initialValue() {
            return new GatewayThreadLocalContext();
        }
    };
    public static GatewayThreadLocalContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    public ServerWebExchange getExchange() {
        return exchange;
    }

    public void setExchange(ServerWebExchange exchange) {
        this.exchange = exchange;
    }
}
