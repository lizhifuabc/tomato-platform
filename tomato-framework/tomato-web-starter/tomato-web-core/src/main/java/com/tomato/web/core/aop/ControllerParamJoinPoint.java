package com.tomato.web.core.aop;

import com.tomato.jackson.utils.JacksonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.UUID;

/**
 * controller参数打印
 *
 * @author lizhifu
 */
@Slf4j
@Aspect
@EnableAspectJAutoProxy
public class ControllerParamJoinPoint {

	@Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
	public void controllerClasses() {
	}

	@Pointcut("execution(* *(..))")
	public void controllerMethods() {
	}

	@Around("controllerClasses() && controllerMethods()")
	public Object paramValidate(ProceedingJoinPoint jp) throws Throwable {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
			.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		log.info("[请求{}] 接口:{}#{},请求url:{},请求类型:{},IP:{},入参:{}", uuid, jp.getSignature().getDeclaringTypeName(),
				jp.getSignature().getName(), request.getRequestURI(), request.getMethod(), request.getRemoteAddr(),
				toJsonString(jp.getArgs()));
		long start = System.currentTimeMillis();
		Object result = jp.proceed();
		log.info("[响应{}] 耗时:{}ms,出参:{}", uuid, (System.currentTimeMillis() - start), toJsonString(result));
		return result;
	}

	private String toJsonString(Object obj) {
		if (Objects.isNull(obj)) {
			return null;
		}
		return JacksonUtils.toJson(obj);
	}

}
