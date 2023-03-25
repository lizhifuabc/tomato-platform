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
