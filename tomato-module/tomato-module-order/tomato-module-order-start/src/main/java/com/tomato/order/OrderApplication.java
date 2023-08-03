package com.tomato.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目启动类
 * @author lizhifu
 */
@SpringBootApplication(scanBasePackages = {"com.tomato"})
@EnableAsync // 开启异步支持
@EnableScheduling
@EnableFeignClients(basePackages = {"com.tomato.merchant.api"})
@MapperScan("com.tomato.order.infrastructure.mapper")
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
