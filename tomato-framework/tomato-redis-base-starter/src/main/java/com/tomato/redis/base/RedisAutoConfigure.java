package com.tomato.redis.base;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * redis 配置类
 *
 * @author lizhifu
 * @since 2023/3/25
 */
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
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }
}
