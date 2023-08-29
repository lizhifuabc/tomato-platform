package com.tomato.idempotent.aspect;

import com.tomato.idempotent.annotation.Idempotent;
import com.tomato.idempotent.autoconfigure.IdempotentAutoConfiguration;
import com.tomato.idempotent.strategy.IdempotentStrategyFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 幂等切面
 *
 * @author lizhifu
 * @since 2023/4/11
 */
@Aspect
public class IdempotentAspect {

	private final IdempotentStrategyFactory idempotentStrategyFactory;

	private static final Logger log = LoggerFactory.getLogger(IdempotentAutoConfiguration.class);

	public IdempotentAspect(IdempotentStrategyFactory idempotentStrategyFactory) {
		this.idempotentStrategyFactory = idempotentStrategyFactory;
	}

	@Before("@annotation(idempotent)")
	public void idempotentHandler(JoinPoint joinPoint, Idempotent idempotent) {
		idempotentStrategyFactory.getStrategy(idempotent.strategy()).execute(joinPoint, idempotent);
		log.info("[beforePointCut][方法({}) 参数({}) 存在重复请求]", joinPoint.getSignature().toString(), joinPoint.getArgs());
	}

}
