package com.tomato.cloud.alibaba;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * 自动配置
 *
 * @author lizhifu
 * @since 2023/5/2
 */
@AutoConfiguration
public class AlibabaAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AlibabaAutoConfiguration.class);
    @PostConstruct
    public void postConstruct() {
        log.info("tomato-cloud-alibaba-starter 自动配置");
    }
}
