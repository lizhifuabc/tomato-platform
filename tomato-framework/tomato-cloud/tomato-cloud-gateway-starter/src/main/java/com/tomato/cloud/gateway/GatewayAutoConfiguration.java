package com.tomato.cloud.gateway;

import com.tomato.cloud.gateway.handler.GatewayExceptionHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;


/**
 * 网关自动配置
 *
 * @author lizhifu
 * @since 2023/7/18
 */
@AutoConfiguration
@Slf4j
public class GatewayAutoConfiguration {
    @PostConstruct
    public void init() {
        log.info("tomato-cloud-gateway-starter 网关自动配置");
    }
    @Bean
    @Primary
    @Order(-2) // 保证优先级高于默认的 Spring Cloud Gateway 的 ErrorWebExceptionHandler 实现
    public GatewayExceptionHandler gatewayExceptionHandler() {
        log.info("tomato-cloud-gateway-starter 网关统一异常处理");
        return new GatewayExceptionHandler();
    }
}
