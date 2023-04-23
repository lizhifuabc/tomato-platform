package com.tomato.notice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目启动类
 * @author lizhifu
 */
@SpringBootApplication
@EnableAsync // 开启异步支持
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "通知系统 API", version = "1.0-SNAPSHOT", description = "通知系统 Information"),
		servers = {@Server(url = "http://localhost:9996/")})
public class NoticeApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(NoticeApplication.class, args);
		Environment env = applicationContext.getEnvironment();
		System.out.println("系统服务启动成功" + env);
		// swagger-ui.html 地址
		System.out.println("http://localhost:"+env.getProperty("server.port")+"/swagger-ui.html");
	}

}
