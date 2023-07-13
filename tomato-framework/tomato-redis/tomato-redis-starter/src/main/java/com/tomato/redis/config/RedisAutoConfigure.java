package com.tomato.redis.config;

import com.tomato.redis.ratelimit.RedisConcurrentRequestCountLimiter;
import com.tomato.redis.ratelimit.RedisRateLimiter;
import com.tomato.redis.utils.RedisBitMapUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.List;

/**
 * redis 配置类
 *
 * @author lizhifu
 * @date 2022/12/9
 */
@AutoConfiguration
public class RedisAutoConfigure {
    /**
     * RedisLockRegistry锁机制
     * @since 2023年03月25日13:14:59
     */
    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory factory){
        // RedisLockType.SPIN_LOCK - the lock is acquired by periodic loop (100ms) checking whether the lock can be acquired. Default.
        // RedisLockType.PUB_SUB_LOCK - The lock is acquired by redis pub-sub subscription.
        return new RedisLockRegistry(factory, "REDIS-LOCK");
    }
    @Bean
    public RedisScript redisRequestRateLimiterScript() {
        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("META-INF/scripts/request_rate_limiter.lua")));
        redisScript.setResultType(List.class);
        return redisScript;
    }
    @Bean
    public RedisScript<Long> redisConcurrentRequestCountLimiterScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("META-INF/scripts/concurrent_request_count_limiter.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }
    @Bean
    @ConditionalOnMissingBean
    public RedisRateLimiter redisRateLimiter(StringRedisTemplate stringRedisTemplate,
                                             @Qualifier("redisRequestRateLimiterScript") RedisScript<List<Long>> redisScript){
        return new RedisRateLimiter(stringRedisTemplate,redisScript);
    }
    @Bean
    @ConditionalOnMissingBean
    public RedisConcurrentRequestCountLimiter redisConcurrentRequestCountLimiter(StringRedisTemplate stringRedisTemplate,
                                                                                 @Qualifier("redisConcurrentRequestCountLimiterScript") RedisScript<Long> redisScript){
        return new RedisConcurrentRequestCountLimiter(stringRedisTemplate,redisScript);
    }
    /**
     * RedisTemplate配置
     * TODO 添加 @LoadBalanced 注解 service-product 实现客户端负载均衡
     *
     * @param factory RedisConnectionFactory
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // 设置hash key序列化方式
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // 设置value序列化方式
        redisTemplate.setValueSerializer(RedisSerializer.java());
        // 设置hash value序列化方式
        redisTemplate.setHashValueSerializer(RedisSerializer.java());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
    @Bean
    public RedisBitMapUtils redisUtil(StringRedisTemplate stringRedisTemplate) {
        return new RedisBitMapUtils(stringRedisTemplate);
    }
}
