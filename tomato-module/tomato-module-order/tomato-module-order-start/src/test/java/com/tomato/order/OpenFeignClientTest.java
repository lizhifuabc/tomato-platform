package com.tomato.order;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试 openFeign
 *
 * @author lizhifu
 * @since 2023/9/14
 */
@SpringBootTest
public class OpenFeignClientTest {
	@Resource
	CircuitBreakerRegistry circuitBreakerRegistry;
	@Test
	public void test() {
		circuitBreakerRegistry.getAllCircuitBreakers().forEach(circuitBreaker -> {
			System.out.println("断路器名称："+circuitBreaker.getName());
		});
	}
}
