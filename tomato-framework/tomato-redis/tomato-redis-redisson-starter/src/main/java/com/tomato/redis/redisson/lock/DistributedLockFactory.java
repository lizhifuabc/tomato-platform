package com.tomato.redis.redisson.lock;

/**
 * 分布式锁工厂
 *
 * @author lizhifu
 * @since 2023/7/13
 */
public interface DistributedLockFactory {
    /**
     * 获取分布式锁
     * @param key 锁的key
     * @return 分布式锁
     */
    DistributedLock getLock(String key);
}
