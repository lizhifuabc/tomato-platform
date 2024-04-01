package com.tomato.dynamic.db.aop;

import com.tomato.dynamic.db.annotation.DataSource;
import com.tomato.dynamic.db.holder.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 切面DataSource
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@Aspect
@Component
@Slf4j
public class DataSourceAspect {

	/**
	 * 设置DataSource注解的切点表达式
	 */
	@Pointcut("@annotation(com.tomato.dynamic.db.annotation.DataSource)")
	public void dynamicDataSourcePointCut() {

	}

	/**
	 * 环绕通知
	 * @param joinPoint 切点
	 * @return Object
	 * @throws Throwable Throwable
	 */
	@Around("dynamicDataSourcePointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		// 注解获取数据源
		String key = getDefineAnnotation(joinPoint).value();
		// 当前数据源
		String currentKey = DynamicDataSourceHolder.getDynamicDataSourceKey();
		if (key == null || key.equals(currentKey)) {
			// 数据源相同，不需要切换
			return joinPoint.proceed();
		}

		DynamicDataSourceHolder.setDynamicDataSourceKey(key);
		try {
			return joinPoint.proceed();
		}
		finally {
			DynamicDataSourceHolder.removeDynamicDataSourceKey();
			// 切换回原来的数据源
			log.info("数据源切回至：{}", DynamicDataSourceHolder.getDynamicDataSourceKey());
		}
	}

	/**
	 * 先判断方法的注解，后判断类的注解，以方法的注解为准
	 * @param joinPoint 切点
	 * @return DataSource
	 */
	private DataSource getDefineAnnotation(ProceedingJoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		// 方法上的注解
		DataSource dataSourceAnnotation = methodSignature.getMethod().getAnnotation(DataSource.class);
		if (Objects.nonNull(methodSignature)) {
			return dataSourceAnnotation;
		}
		else {
			// 类上的注解
			Class<?> dsClass = joinPoint.getTarget().getClass();
			return dsClass.getAnnotation(DataSource.class);
		}
	}

}