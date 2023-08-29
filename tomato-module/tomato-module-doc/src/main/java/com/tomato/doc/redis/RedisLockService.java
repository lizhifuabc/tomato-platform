package com.tomato.doc.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.ReflectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of {@link ExpirableLockRegistry} providing a distributed lock using
 * Redis. Locks are stored under the key {@code registryKey:lockKey}. Locks expire after
 * (default 60) seconds. Threads unlocking an expired lock will get an
 * {@link IllegalStateException}. This should be considered as a critical error because it
 * is possible the protected resources were compromised.
 * <p>
 * Locks are reentrant.
 * <p>
 * <b>However, locks are scoped by the registry; a lock from a different registry with the
 * same key (even if the registry uses the same 'registryKey') are different locks, and
 * the second cannot be acquired by the same thread while the first is locked.</b>
 * <p>
 * <b>Note: This is not intended for low latency applications.</b> It is intended for
 * resource locking across multiple JVMs.
 * <p>
 * {@link Condition}s are not supported.
 *
 * @author lizhifu
 * @since 2023年03月30日14:14:50
 */
@Slf4j
public final class RedisLockService implements ExpirableLockRegistry, DisposableBean {

	private static final int DEFAULT_CAPACITY = 100_000;

	private final Map<String, RedisLock> locks = new LinkedHashMap<String, RedisLock>(16, 0.75F, true) {

		@Override
		protected boolean removeEldestEntry(Entry<String, RedisLock> eldest) {
			return size() > RedisLockService.this.cacheCapacity;
		}

	};

	private final String clientId = UUID.randomUUID().toString();

	private final StringRedisTemplate redisTemplate;

	private final long expireAfter;

	private int cacheCapacity = DEFAULT_CAPACITY;

	/**
	 * An {@link ExecutorService} to call {@link StringRedisTemplate#delete} in the
	 * separate thread when the current one is interrupted.
	 */
	private Executor executor = Executors.newCachedThreadPool(new CustomizableThreadFactory("redis-lock-registry-"));

	/**
	 * Flag to denote whether the {@link ExecutorService} was provided via the setter and
	 * thus should not be shutdown when {@link #destroy()} is called
	 */
	private boolean executorExplicitlySet;

	private volatile boolean unlinkAvailable = true;

	/**
	 * Constructs a lock registry with the supplied lock expiration.
	 * @param connectionFactory The connection factory.
	 */
	public RedisLockService(RedisConnectionFactory connectionFactory) {
		this.redisTemplate = new StringRedisTemplate(connectionFactory);
		// 60 seconds
		this.expireAfter = 60000L;
	}

	/**
	 * Set the {@link Executor}, where is not provided then a default of cached thread
	 * pool Executor will be used.
	 * @param executor the executor service
	 * @since 5.0.5
	 */
	public void setExecutor(Executor executor) {
		this.executor = executor;
		this.executorExplicitlySet = true;
	}

	/**
	 * Set the capacity of cached locks.
	 * @param cacheCapacity The capacity of cached lock, (default 100_000).
	 * @since 5.5.6
	 */
	public void setCacheCapacity(int cacheCapacity) {
		this.cacheCapacity = cacheCapacity;
	}

	@Override
	public Lock obtain(Object lockKey) {
		String path = (String) lockKey;
		synchronized (this.locks) {
			return this.locks.computeIfAbsent(path, RedisSpinLock::new);
		}
	}

	@Override
	public void expireUnusedOlderThan(long age) {
		long now = System.currentTimeMillis();
		synchronized (this.locks) {
			this.locks.entrySet().removeIf(entry -> {
				RedisLock lock = entry.getValue();
				long lockedAt = lock.getLockedAt();
				return now - lockedAt > age
						// 'lockedAt = 0' means that the lock is still not acquired!
						&& lockedAt > 0 && !lock.isAcquiredInThisProcess();
			});
		}
	}

	@Override
	public void destroy() {
		if (!this.executorExplicitlySet) {
			((ExecutorService) this.executor).shutdown();
		}
	}

	private abstract class RedisLock implements Lock {

		private static final String OBTAIN_LOCK_SCRIPT = "local lockClientId = redis.call('GET', KEYS[1]) "
				+ "if lockClientId == ARGV[1] then " + "  redis.call('PEXPIRE', KEYS[1], ARGV[2]) " + "  return true "
				+ "elseif not lockClientId then " + "  redis.call('SET', KEYS[1], ARGV[1], 'PX', ARGV[2]) "
				+ "  return true " + "end " + "return false";

		protected static final RedisScript<Boolean> OBTAIN_LOCK_REDIS_SCRIPT = new DefaultRedisScript<>(
				OBTAIN_LOCK_SCRIPT, Boolean.class);

		protected final String lockKey;

		private final ReentrantLock localLock = new ReentrantLock();

		private volatile long lockedAt;

		private RedisLock(String path) {
			this.lockKey = path;
		}

		public long getLockedAt() {
			return this.lockedAt;
		}

		/**
		 * Attempt to acquire a lock in redis.
		 * @param time the maximum time(milliseconds) to wait for the lock, -1 infinity
		 * @return true if the lock was acquired and false if the waiting time elapsed
		 * before the lock was acquired
		 * @throws InterruptedException – if the current thread is interrupted while
		 * acquiring the lock (and interruption of lock acquisition is supported)
		 */
		protected abstract boolean tryRedisLockInner(long time) throws ExecutionException, InterruptedException;

		/**
		 * Unlock the lock using the unlink method in redis.
		 */
		protected abstract void removeLockKeyInnerUnlink();

		/**
		 * Unlock the lock using the delete method in redis.
		 */
		protected abstract void removeLockKeyInnerDelete();

		@Override
		public final void lock() {
			// 1. 获取本地锁，防止多个线程同时获取redis锁，造成锁竞争，降低性能，同时保证同一线程可以重入
			this.localLock.lock();
			// 2. 尝试获取redis锁，如果获取失败，阻塞等待
			while (true) {
				try {
					// 2.1 尝试获取redis锁，如果获取失败，阻塞等待
					if (tryRedisLock(-1L)) {
						return;
					}
				}
				catch (InterruptedException e) {
					/*
					 * This method must be uninterruptible so catch and ignore interrupts
					 * and only break out of the while loop when we get the lock.
					 */
				}
				catch (Exception e) {
					// 2.2 如果获取redis锁被中断，释放本地锁，抛出无法获取锁异常
					this.localLock.unlock();
					rethrowAsLockException(e);
				}
			}
		}

		private void rethrowAsLockException(Exception e) {
			throw new CannotAcquireLockException("Failed to lock mutex at " + this.lockKey, e);
		}

		@Override
		public final void lockInterruptibly() throws InterruptedException {
			this.localLock.lockInterruptibly();
			while (true) {
				try {
					if (tryRedisLock(-1L)) {
						return;
					}
				}
				catch (InterruptedException ie) {
					this.localLock.unlock();
					Thread.currentThread().interrupt();
					throw ie;
				}
				catch (Exception e) {
					this.localLock.unlock();
					rethrowAsLockException(e);
				}
			}
		}

		@Override
		public final boolean tryLock() {
			try {
				return tryLock(0, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return false;
			}
		}

		@Override
		public final boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
			if (!this.localLock.tryLock(time, unit)) {
				return false;
			}
			try {
				long waitTime = TimeUnit.MILLISECONDS.convert(time, unit);
				boolean acquired = tryRedisLock(waitTime);
				if (!acquired) {
					this.localLock.unlock();
				}
				return acquired;
			}
			catch (Exception e) {
				this.localLock.unlock();
				rethrowAsLockException(e);
			}
			return false;
		}

		private boolean tryRedisLock(long time) throws ExecutionException, InterruptedException {
			final boolean result = tryRedisLockInner(time);
			if (result) {
				this.lockedAt = System.currentTimeMillis();
			}
			return result;
		}

		protected final Boolean obtainLock() {
			return RedisLockService.this.redisTemplate.execute(OBTAIN_LOCK_REDIS_SCRIPT,
					Collections.singletonList(this.lockKey), RedisLockService.this.clientId,
					String.valueOf(RedisLockService.this.expireAfter));
		}

		@Override
		public final void unlock() {
			if (!this.localLock.isHeldByCurrentThread()) {
				throw new IllegalStateException("You do not own lock at " + this.lockKey);
			}
			if (this.localLock.getHoldCount() > 1) {
				this.localLock.unlock();
				return;
			}
			try {
				if (!isAcquiredInThisProcess()) {
					throw new IllegalStateException("Lock was released in the store due to expiration. "
							+ "The integrity of data protected by this lock may have been compromised.");
				}

				if (Thread.currentThread().isInterrupted()) {
					RedisLockService.this.executor.execute(this::removeLockKey);
				}
				else {
					removeLockKey();
				}
				log.info("Released lock; " + this);
			}
			catch (Exception e) {
				ReflectionUtils.rethrowRuntimeException(e);
			}
			finally {
				this.localLock.unlock();
			}
		}

		private void removeLockKey() {
			if (RedisLockService.this.unlinkAvailable) {
				try {
					removeLockKeyInnerUnlink();
					return;
				}
				catch (Exception ex) {
					RedisLockService.this.unlinkAvailable = false;
					log.error("The UNLINK command has failed (not supported on the Redis server?); "
							+ "falling back to the regular DELETE command", ex);
				}
			}
			removeLockKeyInnerDelete();
		}

		@Override
		public final Condition newCondition() {
			throw new UnsupportedOperationException("Conditions are not supported");
		}

		public final boolean isAcquiredInThisProcess() {
			return RedisLockService.this.clientId
				.equals(RedisLockService.this.redisTemplate.boundValueOps(this.lockKey).get());
		}

		@Override
		public String toString() {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss.SSS");
			return "RedisLock [lockKey=" + this.lockKey + ",lockedAt=" + dateFormat.format(new Date(this.lockedAt))
					+ ", clientId=" + RedisLockService.this.clientId + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((this.lockKey == null) ? 0 : this.lockKey.hashCode());
			result = prime * result + (int) (this.lockedAt ^ (this.lockedAt >>> 32)); // NOSONAR
																						// magic
																						// number
			result = prime * result + RedisLockService.this.clientId.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			RedisLock other = (RedisLock) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (!this.lockKey.equals(other.lockKey)) {
				return false;
			}
			return this.lockedAt == other.lockedAt;
		}

		private RedisLockService getOuterType() {
			return RedisLockService.this;
		}

	}

	private final class RedisSpinLock extends RedisLock {

		private RedisSpinLock(String path) {
			super(path);
		}

		@Override
		protected boolean tryRedisLockInner(long time) throws InterruptedException {
			long now = System.currentTimeMillis();
			// -1L means wait forever
			if (time == -1L) {
				while (!obtainLock()) {
					Thread.sleep(100); // NOSONAR
				}
				return true;
			}
			else {
				long expire = now + TimeUnit.MILLISECONDS.convert(time, TimeUnit.MILLISECONDS);
				boolean acquired;
				while (!(acquired = obtainLock()) && System.currentTimeMillis() < expire) { // NOSONAR
					Thread.sleep(100); // NOSONAR
				}
				return acquired;
			}
		}

		@Override
		protected void removeLockKeyInnerUnlink() {
			RedisLockService.this.redisTemplate.unlink(this.lockKey);
		}

		@Override
		protected void removeLockKeyInnerDelete() {
			RedisLockService.this.redisTemplate.delete(this.lockKey);
		}

	}

}
