package com.tomato.book.redis.structure;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

/**
 * 分布式id
 *
 * @author lizhifu
 * @since 2023/2/27
 */
@Service
public class DistributedId {

	private final StringRedisTemplate stringRedisTemplate;

	public DistributedId(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 生成id，步长为1
	 * @return id
	 */
	public Long generateId(String key) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
		return entityIdCounter.getAndIncrement();
	}

	/**
	 * 生成id，步长为increment
	 * @param key key
	 * @param increment 步长
	 * @return id
	 */
	public Long generateId(String key, int increment) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
		return entityIdCounter.getAndAdd(increment);
	}

	/**
	 * 获取id
	 * @param key key
	 * @return id
	 */
	public Long getId(String key) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
		return entityIdCounter.get();
	}

}
