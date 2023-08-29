package com.tomato.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

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
public class OrderApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderApplication.class, args);
		Environment env = applicationContext.getEnvironment();
		System.out.println("系统服务启动成功" + env);
		// swagger-ui.html 地址
		System.out.println("http://localhost:" + env.getProperty("server.port") + "/swagger-ui.html");
	}

}
