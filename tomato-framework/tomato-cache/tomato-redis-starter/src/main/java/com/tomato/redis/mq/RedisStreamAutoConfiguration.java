package com.tomato.redis.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis stream 配置
 *
 * @author lizhifu
 * @since 2023/9/1
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "redis.custom.stream.enable", havingValue = "true" )
@EnableConfigurationProperties({ CustomRedisStreamProperties.class })
public class RedisStreamAutoConfiguration {
	private final CustomRedisStreamProperties customRedisStreamProperties;

	private final StringRedisTemplate stringRedisTemplate;

	public RedisStreamAutoConfiguration(CustomRedisStreamProperties customRedisStreamProperties, StringRedisTemplate stringRedisTemplate) {
		this.customRedisStreamProperties = customRedisStreamProperties;
		this.stringRedisTemplate = stringRedisTemplate;
	}
}
