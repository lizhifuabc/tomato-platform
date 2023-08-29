package com.tomato.lock.redisson.exe;

import com.tomato.lock.core.exe.AbstractLockExe;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Redisson 实现分布式锁
 *
 * @author lizhifu
 * @since 2023/1/17
 */
public class RedissonLockExe extends AbstractLockExe<RLock> {

	private final RedissonClient redissonClient;

	public RedissonLockExe(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

	@Override
	public RLock lock(String lockKey, long expire, long acquireTimeout) {
		try {
			final RLock lockInstance = redissonClient.getLock(lockKey);
			final boolean locked = lockInstance.tryLock(acquireTimeout, expire, TimeUnit.MILLISECONDS);
			return locked ? lockInstance : null;
		}
		catch (InterruptedException e) {
			return null;
		}
	}

	@Override
	public boolean unLock(String lockKey, RLock lockInstance) {
		if (lockInstance.isHeldByCurrentThread()) {
			try {
				return lockInstance.forceUnlockAsync().get();
			}
			catch (ExecutionException | InterruptedException e) {
				return false;
			}
		}
		return false;
	}

}
