package com.tomato.redis.redisson.semaphore;

/**
 * 分布式信号量工厂
 *
 * @author lizhifu
 * @since 2023/7/13
 */
public interface DistributedSemaphoreFactory {
    /**
     * 获取分布式信号量
     * @param key 信号量的key
     * @return 分布式信号量
     */
    DistributedSemaphore getDistributedSemaphore(String key);
}
