package com.tomato.redis.redisson.config;

import com.tomato.redis.redisson.lock.RedissonDistributedLockFactory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * Redisson配置
 *
 * @author lizhifu
 * @since 2023/7/12
 */
@AutoConfiguration
@Slf4j
public class RedissonConfig {
    @PostConstruct
    public void postConstruct() {
        log.info("tomato-redis-redisson-starter 自动装配");
    }
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(@Value("classpath:/redisson.yaml") Resource configFile) throws IOException {
        Config config = Config.fromYAML(configFile.getInputStream());
        return Redisson.create(config);
    }
    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }
    @Bean
    RedissonDistributedLockFactory redissonDistributedLockFactory(RedissonClient redissonClient) {
        return new RedissonDistributedLockFactory(redissonClient);
    }
}
