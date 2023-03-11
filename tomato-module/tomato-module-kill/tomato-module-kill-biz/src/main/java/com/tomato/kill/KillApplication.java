package com.tomato.kill;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 项目启动类
 * @author lizhifu
 */
@SpringBootApplication
@EnableDiscoveryClient
public class KillApplication {

	public static void main(String[] args) {
		SpringApplication.run(KillApplication.class, args);
	}

}
