package com.tomato.idempotent.strategy.impl;

import com.tomato.common.domain.resp.Resp;
import com.tomato.common.exception.BusinessException;
import com.tomato.idempotent.annotation.Idempotent;
import com.tomato.idempotent.strategy.AbstractIdempotentStrategy;
import org.aspectj.lang.JoinPoint;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

/**
 * 幂等 Redis
 *
 * @author lizhifu
 * @since 2023/4/11
 */
@Service
public class RedisStrategyImpl extends AbstractIdempotentStrategy {

	private final StringRedisTemplate redisTemplate;

	public RedisStrategyImpl(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void doBefore(JoinPoint joinPoint, Idempotent idempotent) {
		String methodName = joinPoint.getSignature().toString();
		String argsStr = Arrays.toString(joinPoint.getArgs());
		String redisKey = generateRequestKey(methodName + argsStr);
		// 如果存在，说明已经请求过了
		// 设置过期时间，防止死锁
		Boolean aBoolean = redisTemplate.opsForValue()
			.setIfAbsent(Objects.requireNonNull(redisKey), "", idempotent.timeout(), idempotent.timeUnit());
		if (Boolean.FALSE.equals(aBoolean)) {
			throw new BusinessException(idempotent.message());
		}
		KEY_CACHE.set(redisKey);
	}

	@Override
	public void doAfterReturning(JoinPoint joinPoint, Idempotent idempotent, Object jsonResult){
		if (jsonResult instanceof Resp<?> r) {
			try {
				// 成功不删除redis数据 保证在有效时间内无法重复提交
				if (r.isSuccess()) {
					return;
				}
				redisTemplate.delete(KEY_CACHE.get());
			} finally {
				KEY_CACHE.remove();
			}
		}
	}

	@Override
	public void doAfterThrowing(JoinPoint joinPoint, Idempotent idempotent, Exception e){
		redisTemplate.delete(KEY_CACHE.get());
		KEY_CACHE.remove();
	}

	@Override
	public String name() {
		return "REDIS";
	}

}
