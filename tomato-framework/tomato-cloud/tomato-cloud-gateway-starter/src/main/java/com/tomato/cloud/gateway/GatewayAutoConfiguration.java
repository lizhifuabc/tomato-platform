package com.tomato.cloud.gateway;

import com.tomato.cloud.gateway.filter.GlobalResponseFilter;
import com.tomato.cloud.gateway.gray.configuration.GrayLoadBalancerClientConfiguration;
import com.tomato.cloud.gateway.gray.properties.GrayProperties;
import com.tomato.cloud.gateway.gray.version.CompositeGrayVersionStrategy;
import com.tomato.cloud.gateway.gray.version.GrayVersionStrategy;
import com.tomato.cloud.gateway.gray.version.HeaderGrayVersionStrategy;
import com.tomato.cloud.gateway.gray.version.WeightGrayVersionStrategy;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * 灰度配置
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

}
