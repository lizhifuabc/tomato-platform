package com.tomato.order;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目启动类
 *
 * @author lizhifu
 */
@SpringBootApplication(scanBasePackages = { "com.tomato" })
@EnableAsync // 开启异步支持
@EnableScheduling
@EnableFeignClients(basePackages = { "com.tomato.merchant.api", "com.tomato.channel.api", "com.tomato.account.api",
		"com.tomato.notice.api" })
@MapperScan("com.tomato.order.infrastructure.mapper")
@RestController
public class OrderApplication {
	@Resource
	private CircuitBreakerRegistry circuitBreakerRegistry;
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderApplication.class, args);
		Environment env = applicationContext.getEnvironment();
		System.out.println("系统服务启动成功" + env);
		// swagger-ui.html 地址
		System.out.println("http://localhost:" + env.getProperty("server.port") + "/swagger-ui.html");
	}

	@GetMapping("/test")
	public void test() {
		circuitBreakerRegistry.getAllCircuitBreakers().forEach(circuitBreaker -> {
			System.out.println("断路器名称：" + circuitBreaker.getName());
		});
	}
}
