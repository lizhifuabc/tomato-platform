package com.tomato.cloud.feign;

import com.tomato.cloud.feign.config.FeignLoggerConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 自动配置
 *
 * @author lizhifu
 * @since 2023/5/2
 */
@Configuration(proxyBeanMethods = false)
@Import({FeignLoggerConfiguration.class})
public class AutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AutoConfiguration.class);
    @PostConstruct
    public void postConstruct() {
        log.info("spring cloud feign 自动配置");
    }
}
