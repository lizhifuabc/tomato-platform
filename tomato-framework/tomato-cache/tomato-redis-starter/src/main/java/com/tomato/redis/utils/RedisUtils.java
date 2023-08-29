package com.tomato.redis.utils;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;


/**
 * redis工具类
 *
 * @author lizhifu
 * @since 2023/8/29
 */
public class RedisUtils {
	private final StringRedisTemplate stringRedisTemplate;

	public RedisUtils(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 批量获取 key
	 * @param keyPrefix key前缀
	 * @param pageSize 每页大小
	 * @return Set<String> key集合
	 */
	public Set<String> scanKeys(String keyPrefix, long pageSize) {
		Set<String> keys = new HashSet<>();
		RedisConnection connection = stringRedisTemplate.getRequiredConnectionFactory().getConnection();
		ScanOptions scanOptions = ScanOptions.scanOptions()
				.count(pageSize)
				.match(keyPrefix)
				.build();
		try (Cursor<byte[]> cursor = connection.keyCommands().scan(scanOptions)){
			while (cursor.hasNext()) {
				keys.add(new String(cursor.next()));
			}
			return keys;
		}
	}
}
