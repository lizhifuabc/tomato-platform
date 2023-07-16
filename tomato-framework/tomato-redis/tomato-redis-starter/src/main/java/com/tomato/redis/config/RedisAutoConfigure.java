package com.tomato.redis.config;

import com.tomato.redis.properties.TencentRedisProperties;
import com.tomato.redis.ratelimit.RedisConcurrentRequestCountLimiter;
import com.tomato.redis.ratelimit.RedisRateLimiter;
import com.tomato.redis.service.DefaultTenantContextService;
import com.tomato.redis.service.TenantContextService;
import com.tomato.redis.support.TenantPrefixStringRedisSerializer;
import com.tomato.redis.support.RedisTemplateCustomizer;
import com.tomato.redis.utils.RedisBitMapUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
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
@EnableConfigurationProperties(TencentRedisProperties.class)
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
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory,ObjectProvider<RedisTemplateCustomizer> customizers) {
        RedisTemplate<String, Object> redisTemplate = prefixRedisTemplate(factory, customizers);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
    /**
     * 解析租户标识的 RedisTemplate
     *
     * @param redisConnectionFactory RedisConnectionFactory
     * @param customizers            ObjectProvider<RedisTemplateCustomizer>
     * @return RedisTemplate<String, Object>
     */
    @Bean("prefixRedisTemplate")
    @ConditionalOnProperty(prefix = "spring.data.redis", name = "tenant", havingValue = "true", matchIfMissing = true)
    public RedisTemplate<String, Object> prefixRedisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                       ObjectProvider<RedisTemplateCustomizer> customizers) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(new TenantPrefixStringRedisSerializer(key -> tenantContextService().getTenant(key)));
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        customizers.orderedStream().forEach((customizer) -> customizer.customize(redisTemplate));
        return redisTemplate;
    }
    /**
     * 解析租户标识的 StringRedisTemplate
     *
     * @param redisConnectionFactory RedisConnectionFactory
     * @return prefixStringRedisTemplate
     */
    @Bean("prefixStringRedisTemplate")
    @ConditionalOnProperty(prefix = "spring.data.redis", name = "tenant", havingValue = "true", matchIfMissing = true)
    public StringRedisTemplate prefixStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
        stringRedisTemplate.setKeySerializer(new TenantPrefixStringRedisSerializer(key -> tenantContextService().getTenant(key)));
        stringRedisTemplate.setValueSerializer(RedisSerializer.string());
        stringRedisTemplate.setHashKeySerializer(RedisSerializer.string());
        stringRedisTemplate.setHashValueSerializer(RedisSerializer.string());
        return stringRedisTemplate;
    }
    /**
     * 不解析标识的stringRedisTemplate
     *
     * @param redisConnectionFactory RedisConnectionFactory
     * @return StringRedisTemplate
     */
    @Bean("stringRedisTemplate")
    public StringRedisTemplate noneTenantStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = prefixStringRedisTemplate(redisConnectionFactory);
        stringRedisTemplate.setKeySerializer(RedisSerializer.string());
        return stringRedisTemplate;
    }
    @Bean
    @ConditionalOnMissingBean(TenantContextService.class)
    @ConditionalOnProperty(prefix = "spring.data.redis", name = "tenant", havingValue = "true", matchIfMissing = true)
    TenantContextService tenantContextService(){
        return new DefaultTenantContextService();
    }
    @Bean
    public RedisBitMapUtils redisUtil(StringRedisTemplate stringRedisTemplate) {
        return new RedisBitMapUtils(stringRedisTemplate);
    }
}
