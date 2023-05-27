package com.tomato.reconciliation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动
 *
 * @author lizhifu
 */
@EnableAsync
@SpringBootApplication
@ConfigurationPropertiesScan
public class ReconciliationApplication {

	public static void main(String... args) throws Exception {
		SpringApplication.run(ReconciliationApplication.class, args);
	}
}
