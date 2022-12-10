package com.tomato.goods.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * redis lua 脚本加载
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@Configuration
public class RedisScriptConfig {
    @Bean
    public DefaultRedisScript<Long> seckillRedisScript() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/seckill.lua")));
        defaultRedisScript.setResultType(Long.class);
        return defaultRedisScript;
    }
}
