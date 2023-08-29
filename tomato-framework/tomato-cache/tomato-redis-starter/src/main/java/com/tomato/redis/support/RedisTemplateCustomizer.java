package com.tomato.redis.support;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * 自定义RedisTemplate的配置
 *
 * @author lizhifu
 * @since 2023/7/16
 */
public interface RedisTemplateCustomizer {

	/**
	 * redisTemplate 自定义
	 * @param redisTemplate RedisTemplate<String, Object>
	 */
	void customize(RedisTemplate<String, Object> redisTemplate);

}
