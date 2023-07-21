package com.tomato.cloud.feign;

import com.tomato.cloud.feign.config.FeignLoggerConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
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
}
