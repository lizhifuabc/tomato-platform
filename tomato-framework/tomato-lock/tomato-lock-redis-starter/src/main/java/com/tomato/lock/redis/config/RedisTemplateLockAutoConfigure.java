package com.tomato.lock.redis.config;

import com.tomato.lock.core.exe.DistributedLockExe;
import com.tomato.lock.redis.exe.RedisTemplateLockExe;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisTemplate 分布式锁配置
 *
 * @author lizhifu
 * @since 2023/1/17
 */
@AutoConfiguration
public class RedisTemplateLockAutoConfigure {
    @Bean
    @ConditionalOnMissingBean
    DistributedLockExe distributedLockExe(StringRedisTemplate stringRedisTemplate){
        return new RedisTemplateLockExe(stringRedisTemplate);
    }
}
