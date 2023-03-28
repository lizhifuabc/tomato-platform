package com.tomato.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 * @author lizhifu
 * @since 2023年03月28日13:24:04
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GenApplication {
	public static void main(String[] args) {
		SpringApplication.run(GenApplication.class, args);
	}

}
