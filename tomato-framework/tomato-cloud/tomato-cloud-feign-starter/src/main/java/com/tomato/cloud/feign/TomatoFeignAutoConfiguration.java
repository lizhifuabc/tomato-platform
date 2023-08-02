package com.tomato.cloud.feign;

import com.tomato.cloud.feign.config.FeignLoggerConfiguration;
import com.tomato.cloud.feign.config.FeignRequestInterceptor;
import feign.RequestInterceptor;
import io.micrometer.tracing.Tracer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 自动配置
 *
 * @author lizhifu
 * @since 2023/5/2
 */
@AutoConfiguration
@Import({FeignLoggerConfiguration.class})
public class TomatoFeignAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(TomatoFeignAutoConfiguration.class);
    @PostConstruct
    public void postConstruct() {
        log.info("tomato-cloud-feign-starter 自动配置");
    }
    @Bean
    @ConditionalOnMissingBean(FeignRequestInterceptor.class)
    public RequestInterceptor feignRequestInterceptor(Tracer tracer) {
        FeignRequestInterceptor feignRequestInterceptor = new FeignRequestInterceptor(tracer);
        log.info("tomato-cloud-feign-starter feignRequestInterceptor 自动配置");
        return feignRequestInterceptor;
    }
}
