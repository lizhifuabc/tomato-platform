package com.tomato.redis.redisson.semaphore;

import java.util.concurrent.TimeUnit;

/**
 * 分布式信号量
 *
 * @author lizhifu
 * @since 2023/7/13
 */
public interface DistributedSemaphore {

	/**
	 * 获取一个令牌,如果没有可用令牌则阻塞等待。
	 * @return 令牌id
	 * @throws InterruptedException 中断异常
	 */
	String acquire() throws InterruptedException;

	/**
	 * 获取一个令牌,并指定持有令牌的租期时间。
	 * @param leaseTime 租期时间
	 * @param unit 时间单位
	 * @return
	 * @throws InterruptedException 中断异常
	 */
	String acquire(long leaseTime, TimeUnit unit) throws InterruptedException;

	/**
	 * 尝试获取一个令牌,如果没有立即可用则返回null
	 * @return 令牌id
	 */
	String tryAcquire();

	/**
	 * 尝试在指定等待时间内获取一个令牌。
	 * @param waitTime 等待时间
	 * @param unit 时间单位
	 * @return 令牌id
	 * @throws InterruptedException 中断异常
	 */
	String tryAcquire(long waitTime, TimeUnit unit) throws InterruptedException;

	/**
	 * 尝试在指定等待时间内获取一个令牌,并指定持有令牌的租期时间。
	 * @param waitTime 等待时间
	 * @param leaseTime 租期时间
	 * @param unit 时间单位
	 * @return 令牌id
	 * @throws InterruptedException 中断异常
	 */
	String tryAcquire(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

	/**
	 * 尝试释放指定id的令牌。
	 * @param permitId 令牌id
	 * @return true:释放成功
	 */
	boolean tryRelease(String permitId);

	/**
	 * 释放指定id的令牌。
	 * @param permitId 令牌id
	 */
	void release(String permitId);

	/**
	 * 获取当前可用的令牌数量。
	 * @return
	 */
	int availablePermits();

	/**
	 * 尝试设置总令牌数。
	 * @param permits 令牌数
	 * @return true:设置成功
	 */
	boolean trySetPermits(int permits);

	/**
	 * 增加指定数量的令牌。
	 * @param permits 令牌数
	 */
	void addPermits(int permits);

	/**
	 * 更新指定令牌的租期时间。
	 * @param permitId 令牌id
	 * @param leaseTime 租期时间
	 * @param unit 时间单位
	 * @return true:更新成功
	 */
	boolean updateLeaseTime(String permitId, long leaseTime, TimeUnit unit);

}
