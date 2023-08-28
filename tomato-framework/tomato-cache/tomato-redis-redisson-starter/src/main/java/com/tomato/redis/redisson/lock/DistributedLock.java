package com.tomato.redis.redisson.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 * <p>1.互斥性:同时只能有一个线程持有锁
 * <p>2.具有锁失效时间:通过租期(leaseTime)可以自动释放持有过久的锁
 * <p>3.可重入:同一个线程可以多次获取同一把锁
 * <p>4.Fail-Fast:获取锁失败立即返回,不会一直等待获取锁
 * @author lizhifu
 * @since 2023/7/13
 */
public interface DistributedLock {
    /**
     * 尝试获取锁,可以自动释放持有过久的锁,防止死锁
     * @param waitTime 等待时间
     * @param leaseTime 租期时间,指定获取到锁后可以持有的时间,超过这个时间锁会自动失效。
     * @param unit 时间单位
     * @return true:获取成功
     * @throws InterruptedException 中断异常
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

    /**
     * 尝试获取锁
     * @param waitTime 等待时间
     * @param unit 时间单位
     * @return true:获取成功
     * @throws InterruptedException 中断异常
     */
    boolean tryLock(long waitTime,  TimeUnit unit) throws InterruptedException;

    /**
     * 尝试获取锁
     * @return true:获取成功
     * @throws InterruptedException 中断异常
     */
    boolean tryLock() throws InterruptedException;

    /**
     * 获取锁,阻塞等待
     * @param leaseTime 租期时间,指定获取到锁后可以持有的时间,超过这个时间锁会自动失效。
     * @param unit 时间单位
     */
    void lock(long leaseTime, TimeUnit unit);

    /**
     * 释放锁
     */
    void unlock();

    /**
     * 判断锁是否被任何线程持有
     * @return true:被持有
     */
    boolean isLocked();

    /**
     * 判断锁是否被指定线程持有
     * @param threadId 线程id
     * @return true:持有锁
     */
    boolean isHeldByThread(long threadId);

    /**
     * 判断当前线程是否持有锁
     * @return true:持有锁
     */
    boolean isHeldByCurrentThread();
}
