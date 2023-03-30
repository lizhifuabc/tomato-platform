package com.tomato.doc.config;

import com.tomato.doc.redis.RedisLockService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * redis配置
 *
 * @author lizhifu
 * @since 2023/3/30
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisLockService redisLockService(RedisConnectionFactory factory) {
        return new RedisLockService(factory);
    }
}
