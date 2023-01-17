package com.tomato.lock.core.exe;

/**
 * 分布式锁执行
 *
 * @author lizhifu
 * @since 2023/1/17
 */
public interface DistributedLockExe<T> {
    /**
     * 加锁
     *
     * @param lockKey        加锁key
     * @param expire         锁有效时间
     * @param acquireTimeout 获取锁超时时间
     * @return 锁信息
     */
    T lock(String lockKey, long expire, long acquireTimeout);

    /**
     * 解锁
     *
     * <p>
     * lockValue作用：
     * <p>1. 请求 A 执行解锁操作，此时锁已经过期。</p>
     * <p>2. 请求B 加锁成功，有可能 A 的解锁操作将 B 请求的锁解除。</p>
     *
     * @param lockKey          加锁key
     * @param lockInstance 锁实例
     * @return 是否释放成功
     */
    boolean unLock(String lockKey, T lockInstance);
}