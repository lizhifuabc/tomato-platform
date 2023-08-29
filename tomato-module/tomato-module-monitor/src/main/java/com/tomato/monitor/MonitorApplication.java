package com.tomato.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 *
 * @author lizhifu
 */
@EnableAdminServer
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class MonitorApplication {

	public static void main(String[] args) {
		log.info("MonitorApplication start...");
		SpringApplication.run(MonitorApplication.class, args);
	}

}
