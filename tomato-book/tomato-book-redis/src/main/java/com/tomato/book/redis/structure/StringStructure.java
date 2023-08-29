package com.tomato.book.redis.structure;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * redis 数据结构: String 字符串，最基础以及最常用的数据结构， 可以存储任何类型的字符串，比如：数字、图片、视频、音频等。最大能存储512M。 使用场景：
 *
 * @author lizhifu
 * @since 2023/2/27
 */
@Service
public class StringStructure {

	private final StringRedisTemplate stringRedisTemplate;

	public StringStructure(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 设置值
	 * @param key key
	 * @param value value
	 */
	public void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 计数器：自增，每次加1，如果不存在则创建，如果存在则自增； 简单限流：以访问者的ip和其他信息作为key，访问一次增加一次计数，超过次数则返回false，不允许访问。
	 * 异步写入数据库(定时任务写入数据库)
	 * @param key
	 */
	public Long incr(String key) {
		return stringRedisTemplate.opsForValue().increment(key);
	}

	/**
	 * 分布式ID生成器 一次性生成多个ID，缓存在本地中，每次取出一个，用完后再去redis中取。
	 * @param key key
	 * @param increment 自增量
	 * @return
	 */
	public synchronized Long incrBy(String key, Long increment) {
		return stringRedisTemplate.opsForValue().increment(key, increment);
	}

}
