package com.tomato.redis.redisson.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redisson的分布式锁实现
 *
 * @author lizhifu
 * @since 2023/7/13
 */
@Component
@ConditionalOnProperty(value = "spring.data.redis.redisson.enabled", havingValue = "true")
public class RedissonDistributedLockFactory implements DistributedLockFactory{
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public DistributedLock getLock(String key) {
        RLock rLock = redissonClient.getLock(key);
        return new DistributedLock() {
            @Override
            public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
                return rLock.tryLock(waitTime, leaseTime, unit);
            }

            @Override
            public boolean tryLock(long waitTime, TimeUnit unit) throws InterruptedException {
                return rLock.tryLock(waitTime, unit);
            }

            @Override
            public boolean tryLock() throws InterruptedException {
                return rLock.tryLock();
            }

            @Override
            public void lock(long leaseTime, TimeUnit unit) {
                rLock.lock(leaseTime, unit);
            }

            @Override
            public void unlock() {
                // 1. 只有在锁被持有时才释放,避免释放未持有的锁导致错误
                // 2. 只有锁被当前线程持有时才释放,不会释放其他线程持有的锁
                // 3. 利用重入锁的特性,同一线程可以多次获取同一把锁,因此需要多次释放才能完全释放锁
                if (isLocked() && isHeldByCurrentThread()) {
                    rLock.unlock();
                }
            }

            @Override
            public boolean isLocked() {
                return rLock.isLocked();
            }

            @Override
            public boolean isHeldByThread(long threadId) {
                return rLock.isHeldByThread(threadId);
            }

            @Override
            public boolean isHeldByCurrentThread() {
                return rLock.isHeldByCurrentThread();
            }
        };
    }
}
