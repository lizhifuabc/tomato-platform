package com.tomato.cloud.gateway.sentinel;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * Sentinel Spring Cloud Gateway 支持
 *
 * @author lizhifu
 * @since 2023/7/18
 */
@AutoConfiguration
public class GatewaySentinelAutoConfiguration {
    @PostConstruct
    public void init() {
        System.out.println("tomato-cloud-sentinel-gateway-starter 自动装配");
    }
}
