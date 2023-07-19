package com.tomato.cloud.gateway.gray;

import com.tomato.cloud.gateway.gray.configuration.GrayLoadBalancerClientConfiguration;
import com.tomato.cloud.gateway.gray.properties.GrayProperties;
import com.tomato.cloud.gateway.gray.version.GrayVersionStrategy;
import com.tomato.cloud.gateway.gray.version.CompositeGrayVersionStrategy;
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
@EnableConfigurationProperties(GrayProperties.class)
@AutoConfiguration
@LoadBalancerClients(defaultConfiguration = GrayLoadBalancerClientConfiguration.class)
@Slf4j
public class GrayAutoConfiguration {
    @PostConstruct
    public void init() {
        log.info("0灰度配置|tomato-cloud-gateway-starter 灰度自动配置");
    }

    @Bean
    @Primary
    public GrayVersionStrategy grayVersionStrategy(List<GrayVersionStrategy> grayVersionStrategies) {
        log.info("3灰度配置|CompositeGrayVersionStrategy|初始化灰度策略|灰度版本策略列表:{}", grayVersionStrategies);
        return new CompositeGrayVersionStrategy(grayVersionStrategies);
    }

    /**
     * 根据请求头参数获取灰度版本
     * @return HeaderGrayVersionStrategy 根据请求头参数获取灰度版本
     */
    @Bean
    public HeaderGrayVersionStrategy headerGrayVersionStrategy() {
        log.info("1灰度配置|HeaderGrayVersionStrategy|请求头参数获取灰度版本");
        return new HeaderGrayVersionStrategy();
    }

    /**
     * 根据权重获取灰度版本，优先级低于请求头参数{@link HeaderGrayVersionStrategy}
     * @return WeightGrayVersionStrategy 根据权重获取灰度版本
     */
    @Bean
    public WeightGrayVersionStrategy weightGrayVersionStrategy() {
        log.info("2灰度配置|WeightGrayVersionStrategy|根据权重获取灰度版本,优先级低于请求头参数");
        return new WeightGrayVersionStrategy();
    }
}
