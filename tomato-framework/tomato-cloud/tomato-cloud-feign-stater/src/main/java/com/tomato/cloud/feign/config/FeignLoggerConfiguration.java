package com.tomato.cloud.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 开启日志
 *
 * @author lizhifu
 * @since 2023/6/6
 */
@Configuration
public class FeignLoggerConfiguration {
    /**
     * 全局配置日志
     * @return Logger.Level 日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
