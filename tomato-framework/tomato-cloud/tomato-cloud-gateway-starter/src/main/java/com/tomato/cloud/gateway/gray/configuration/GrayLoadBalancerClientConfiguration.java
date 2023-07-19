package com.tomato.cloud.gateway.gray.configuration;

import com.tomato.cloud.gateway.gray.properties.GrayProperties;
import com.tomato.cloud.gateway.gray.version.GrayVersionStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnReactiveDiscoveryEnabled;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 灰度部署的客户端负载均衡配置类
 * <P>1. ConditionalOnDiscoveryEnabled 只有在服务发现Enabled的情况下生效</P>
 * <P>2. spring.cloud.loadbalancer.configurations 配置为 default（加载默认的LoadBalancer配置）或者没有配置时,才会加载这个Bean/P>
 * @author lizhifu
 * @since 2023/7/18
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@Slf4j
public class GrayLoadBalancerClientConfiguration {
    private static final int REACTIVE_SERVICE_INSTANCE_SUPPLIER_ORDER = Integer.MIN_VALUE;
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnReactiveDiscoveryEnabled
    @Order(REACTIVE_SERVICE_INSTANCE_SUPPLIER_ORDER)
    public static class ReactiveSupportConfiguration {
        /**
         * 服务实例列表提供者
         * <P>1. ConditionalOnBean(ReactiveDiscoveryClient.class) 只有在ReactiveDiscoveryClient（服务发现的反应式版本）存在的情况下生效</P>
         * <P>2. ConditionalOnProperty(value = "spring.cloud.loadbalancer.configurations", havingValue = "default", matchIfMissing = true) 配置为 default（加载默认的LoadBalancer配置）或者没有配置时,才会加载这个Bean</P>
         * @param context 上下文
         * @param grayProperties 灰度配置
         * @param grayVersionStrategy 灰度版本策略
         * @return ServiceInstanceListSupplier 服务实例列表提供者
         */
        @Bean
        @ConditionalOnBean(ReactiveDiscoveryClient.class)
        @ConditionalOnProperty(value = "spring.cloud.loadbalancer.configurations", havingValue = "default", matchIfMissing = true)
        public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(ConfigurableApplicationContext context,
                                                                                      GrayProperties grayProperties,
                                                                                      GrayVersionStrategy grayVersionStrategy) {
            log.info("0灰度流程|自定义服务实例列表提供者初始化");
            // 默认服务实例获取,使用服务发现机制来获取可用的服务实例列表
            ServiceInstanceListSupplier serviceInstanceListSupplier = ServiceInstanceListSupplier.builder()
                    .withDiscoveryClient()
                    .build(context);
            // 自定义服务实例获取
            return new GrayServiceInstanceListSupplier(serviceInstanceListSupplier, grayProperties, grayVersionStrategy);
        }
    }
}
