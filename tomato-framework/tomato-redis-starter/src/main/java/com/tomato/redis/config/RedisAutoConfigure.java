package com.tomato.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.jackson.module.JavaTimeModule;
import com.tomato.redis.ratelimit.RedisConcurrentRequestCountLimiter;
import com.tomato.redis.ratelimit.RedisRateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.*;
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
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        return template;
    }
    /**
     * 创建 RedisTemplate Bean，使用 JSON 序列化方式
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建 RedisTemplate 对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置 RedisConnection 工厂
        template.setConnectionFactory(factory);
        // 使用 String 序列化方式，序列化 KEY 。
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        // 使用 JSON 序列化方式（库是 Jackson ），序列化 VALUE 。
        // 区别于Jackson2JsonRedisSerializer，GenericJackson2JsonRedisSerializer,会额外存储序列化对象的包命和类名
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(mapper);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        return template;
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
}
