package com.tomato.cloud.alibaba;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 *
 * @author lizhifu
 * @since 2023/5/2
 */
@Configuration(proxyBeanMethods = false)
public class AutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AutoConfiguration.class);
    @PostConstruct
    public void postConstruct() {
        log.info("spring cloud alibaba 自动配置");
    }
}
