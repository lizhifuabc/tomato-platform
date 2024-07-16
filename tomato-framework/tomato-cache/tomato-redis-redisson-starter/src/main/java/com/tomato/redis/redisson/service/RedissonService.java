package com.tomato.redis.redisson.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RIdGenerator;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * redisson
 *
 * @author lizhifu
 * @since 2024/7/16
 */
@Slf4j
@Service
public class RedissonService {

	@Autowired
	private RedissonClient redissonClient;

	/**
	 * 获取锁
	 *
	 * @param lockKey 加锁key
	 * @param waitTime 毫秒
	 * @param lockTime 毫秒
	 * @param supplier 执行程序
	 */
	public <T> T executeWithLock(String lockKey, long waitTime, long lockTime, Supplier<T> supplier) {
		// 获取锁
		RLock lock = this.tryLock(lockKey, waitTime, lockTime);
		try {
			// 执行程序
			return supplier.get();
		} finally {
			// 释放锁
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	/**
	 * 获取锁 并 执行程序
	 *
	 * @param lockKey
	 * @param waitTime 毫秒
	 * @param lockTime 毫秒
	 * @param runnable
	 */
	public void executeWithLock(String lockKey, long waitTime, long lockTime, Runnable runnable) {
		// 获取锁
		RLock lock = this.tryLock(lockKey, waitTime, lockTime);
		try {
			runnable.run();
		} finally {
			// 释放锁
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	/**
	 * 尝试获取锁
	 * 最多等待 waitTime 毫秒
	 * 获取锁成功后占用 lockTime 毫秒
	 * 手动解锁 lock.unlock()
	 *
	 * @param lockKey 加锁 key
	 * @param waitTime 等待时间，毫秒
	 * @param lockTime 加锁时间，毫秒
	 * @return RLock
	 */
	public RLock tryLock(String lockKey, long waitTime, long lockTime) {
		RLock lock = redissonClient.getLock(lockKey);
		try {
			boolean getLock = lock.tryLock(waitTime, lockTime, TimeUnit.MILLISECONDS);
			if (getLock) {
				return lock;
			}
		} catch (InterruptedException e) {
			log.error("Redisson tryLock", e);
		}
		throw new RuntimeException("业务繁忙,请稍后重试");
	}

	/**
	 * 获取 id 生成器
	 * nextId 可生成连续不重复的id
	 *
	 * @param key ID key
	 * @return RIdGenerator
	 */
	public RIdGenerator idGenerator(String key) {
		return redissonClient.getIdGenerator(key);
	}

	/**
	 * 存放数据
	 *
	 * @param key key
	 * @param v value
	 * @param duration 过期时间
	 */
	public <T> void putObj(String key, T v, Duration duration) {
		redissonClient.getBucket(key).set(v, duration);
	}

	/**
	 * 获取数据
	 *
	 * @param key key
	 * @param clazz 类型
	 * @return 如果没有找到则返回null
	 */
	public <T> T getObj(String key, Class<T> clazz) {
		RBucket<T> bucket = redissonClient.getBucket(key);
		return bucket.get();
	}
}
