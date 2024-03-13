package com.tomato.idempotent.strategy;

import com.tomato.idempotent.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;

/**
 * 幂等执行策略
 *
 * @author lizhifu
 * @since 2023/4/11
 */
public interface IdempotentStrategy {

	/**
	 * 执行前
	 * @param joinPoint 切点
	 * @param idempotent 幂等注解
	 */
	void doBefore(JoinPoint joinPoint, Idempotent idempotent);

	/**
	 * 执行后
	 * @param joinPoint 切点
	 * @param idempotent 幂等注解
	 * @param jsonResult 返回结果
	 */
	default void doAfterReturning(JoinPoint joinPoint, Idempotent idempotent, Object jsonResult){}

	/**
	 * 异常处理
	 * @param joinPoint 切点
	 * @param idempotent 幂等注解
	 * @param e 异常
	 */
	default void doAfterThrowing(JoinPoint joinPoint, Idempotent idempotent, Exception e){}

	/**
	 * 策略名称
	 * @return 策略名称
	 */
	String name();

}
