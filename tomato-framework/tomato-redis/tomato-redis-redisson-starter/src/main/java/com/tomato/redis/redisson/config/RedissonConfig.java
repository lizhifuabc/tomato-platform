package com.tomato.redis.redisson.config;

import com.tomato.redis.redisson.properties.RedissonProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Redisson配置
 *
 * @author lizhifu
 * @since 2023/7/12
 */
@AutoConfiguration
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnProperty(value = "spring.data.redis.redisson.enabled", havingValue = "true")
@Slf4j
public class RedissonConfig {
    @Autowired
    private RedissonProperties redissonProperties;

    @PostConstruct
    public void postConstruct() {
        log.info("tomato-redis-redisson-starter 自动装配");
    }
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        switch (redissonProperties.getMode()) {
            case CLUSTER:
                ClusterServersConfig clusterServersConfig = config.useClusterServers();
                BeanUtils.copyProperties(redissonProperties.getClusterServersConfig(), clusterServersConfig, ClusterServersConfig.class);
                redissonProperties.getClusterServersConfig().getNodeAddresses().parallelStream().forEach(clusterServersConfig::addNodeAddress);
                break;
            case SENTINEL:
                SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
                BeanUtils.copyProperties(redissonProperties.getSentinelServersConfig(), sentinelServersConfig, SentinelServersConfig.class);
                redissonProperties.getSentinelServersConfig().getSentinelAddresses().parallelStream().forEach(sentinelServersConfig::addSentinelAddress);
                break;
            default:
                SingleServerConfig singleServerConfig = config.useSingleServer();
                BeanUtils.copyProperties(redissonProperties.getSingleServerConfig(), singleServerConfig, SingleServerConfig.class);
                break;
        }
        config.setCodec(new JsonJacksonCodec());
        // 默认看门狗的检查锁的超时时间是30s
        config.setLockWatchdogTimeout(1000 * 30);
        RedissonClient redissonClient = Redisson.create(config);
        log.info("tomato-redis-redisson-starter 自动装配 RedissonClient 完成");
        return redissonClient;
    }
}
