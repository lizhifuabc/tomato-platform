package com.tomato.lock.redis.exe;

import com.tomato.lock.core.exe.AbstractLockExe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * RedisTemplate：分布式锁
 * <p>
 * 原子性：lua脚本执行时会将脚本作为一个整体执行，中间不会被其他命令插入
 * </p>
 * <p>
 * 缺点：只作用在一个Redis节点上，例如 master 节点发生了主从切换，分布式锁key尚未同步到slave节点，此时数据消失
 * </p>
 * <p>
 * </p>
 *
 * @author lizhifu
 * @since 2023/1/17
 */
@Slf4j
public class RedisTemplateLockExe extends AbstractLockExe<Boolean> {

	private final StringRedisTemplate stringRedisTemplate;

	private final DefaultRedisScript<Boolean> LOCK_SCRIPT;

	private final DefaultRedisScript<Long> DELETE_LOCK_SCRIPT;

	/**
	 * 作为Redis分布式锁对应key中的value存放到Redis中，保证同一个线程操作同一个key可重用
	 * 具体表现为：当需要获取分布式锁时，先计算当前clientId是否和Redis中对应key的value相等，如果相等，说明是同一个实例，即是同一个线程。
	 * 此时只要重新设置失效时间即可
	 */
	private final String uuid = UUID.randomUUID().toString();

	public RedisTemplateLockExe(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
		LOCK_SCRIPT = new DefaultRedisScript<>();
		LOCK_SCRIPT
			.setScriptSource(new ResourceScriptSource(new ClassPathResource("META-INF/scripts/lock_script.lua")));
		LOCK_SCRIPT.setResultType(Boolean.class);

		DELETE_LOCK_SCRIPT = new DefaultRedisScript<>();
		DELETE_LOCK_SCRIPT.setScriptSource(
				new ResourceScriptSource(new ClassPathResource("META-INF/scripts/delete_lock_script.lua")));
		DELETE_LOCK_SCRIPT.setResultType(Long.class);
	}

	@Override
	public Boolean lock(String lockKey, long expire, long acquireTimeout) {
		Boolean result = stringRedisTemplate.execute(LOCK_SCRIPT, Collections.singletonList(lockKey), uuid,
				String.valueOf(expire));
		// 重试时间间隔 TODO 优化
		long retryDuration = 100;
		// 重试次数
		long retryTimes = acquireTimeout / retryDuration;

		while ((Boolean.FALSE.equals(result)) && retryTimes-- > 0) {
			try {
				log.debug("加锁失败，重试..." + acquireTimeout);
				Thread.sleep(retryDuration);
			}catch (Exception e){
				return false;
			}
			result = stringRedisTemplate.execute(LOCK_SCRIPT, Collections.singletonList(lockKey), uuid,
					String.valueOf(expire));
		}
		return result;
	}

	@Override
	public boolean unLock(String lockKey, Boolean lockInstance) {
		Long unLock = stringRedisTemplate.execute(DELETE_LOCK_SCRIPT, Collections.singletonList(lockKey), uuid);
		return Optional.ofNullable(unLock).map(res -> unLock == 1).orElse(false);
	}

}
