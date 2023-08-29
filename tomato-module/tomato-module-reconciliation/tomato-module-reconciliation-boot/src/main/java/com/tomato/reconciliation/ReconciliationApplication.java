package com.tomato.reconciliation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动
 *
 * @author lizhifu
 */
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan
public class ReconciliationApplication {

	public static void main(String... args) throws Exception {
		SpringApplication.run(ReconciliationApplication.class, args);
	}

}
