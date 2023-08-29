package com.tomato.idempotent.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 重复提交 aop
 *
 * @author lizhifu
 * @since 2023/4/26
 */
@Aspect
@Slf4j
public class RepeatSubmitAspect {

	/**
	 * 环绕通知 TODO 增加删除token,用户级别防止重复提交
	 * @param point 切点
	 * @return Object
	 * @throws Throwable 异常
	 */
	@Around("@annotation(com.tomato.idempotent.annotation.RepeatSubmit)")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return point.proceed();
	}

}
