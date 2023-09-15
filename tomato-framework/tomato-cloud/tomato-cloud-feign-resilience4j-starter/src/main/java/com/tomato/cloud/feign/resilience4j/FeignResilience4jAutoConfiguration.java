package com.tomato.cloud.feign.resilience4j;

import feign.Target;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;

import java.lang.reflect.Method;

/**
 * resilience4j Spring Cloud feign 支持
 * <a href="https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#spring-cloud-feign-circuitbreaker">resilience4j</a>
 * @author lizhifu
 * @since 2023/9/14
 */
@AutoConfiguration
@Slf4j
public class FeignResilience4jAutoConfiguration {
	/**
	 * 重写CircuitBreakerNameResolver
	 * 截至2020.0.2，断路器名称模式已从<feignClientName>_<calledMethod>更改。
	 * 使用2020.0.4中引入的CircuitBreakerNameResolver，断路器名称可以保留旧模式。
	 * @return CircuitBreakerNameResolver
	 */
	@Deprecated
	public CircuitBreakerNameResolver circuitBreakerNameResolver() {
		return (String feignClientName, Target<?> target, Method method) -> feignClientName + "_" + method.getName();
	}
}
