package com.tomato.operator.log.aspect;

import com.tomato.operator.log.annotation.LogRecordAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 * <p>
 * Before: 前置通知, 在方法执行之前执行
 * After: 后置通知, 在方法执行之后执行 。
 * AfterReturning: 返回通知, 在方法返回结果之后执行
 * AfterThrowing: 异常通知, 在方法抛出异常之后
 * Around: 环绕通知, 围绕着方法执行
 *
 * @author lizhifu
 * @since 2024/3/13
 */
@Aspect
@Component
public class LogRecordAspect {
	@Before("@annotation(logRecordAnnotation)")
	public void before(JoinPoint joinPoint, LogRecordAnnotation logRecordAnnotation) {

	}
}
