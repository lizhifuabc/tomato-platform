package com.tomato.order.infrastructure.config;

import com.tomato.common.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义配置
 *
 * @author lizhifu
 * @since 2023/8/8
 */
@Configuration
public class CustomConfig {
    @Bean
    public IdWorker idWorker() {
        // TODO redis 生成workerId
        return new IdWorker(null);
    }
}
