package com.tomato.monitor.prometheus;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 自动配置
 * 为应用程序中的指标数据提供自定义的配置。
 * 通过commonTags方法为每个指标添加了一个标签。这有助于在Prometheus中更好地组织和筛选指标。
 * 同时，Prometheus服务器的配置（在prometheus.yml中的作业配置）将使Prometheus能够定期拉取这些指标数据。
 * @author lizhifu
 * @since 2023/5/2
 */
@AutoConfiguration
public class PrometheusAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(PrometheusAutoConfiguration.class);
    @PostConstruct
    public void postConstruct() {
        log.info("tomato-monitor-prometheus 自动配置");
    }
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
        // # 为指标设置一个Tag，这里设置为应用名，Tag是Prometheus提供的一种能力，从而实现更加灵活的筛选
        log.info("tomato-monitor-prometheus:{} 自动配置",applicationName);
        return (registry) -> registry.config().commonTags("application", applicationName);
    }
}
