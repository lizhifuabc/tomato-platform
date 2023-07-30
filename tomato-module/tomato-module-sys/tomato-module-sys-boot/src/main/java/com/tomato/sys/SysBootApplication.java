package com.tomato.sys;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 项目启动类
 * @author lizhifu
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "系统服务 API", version = "1.0-SNAPSHOT", description = "系统服务 Information"),
		servers = {@Server(url = "http://localhost:8001")})
public class SysBootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SysBootApplication.class, args);
		Environment env = applicationContext.getEnvironment();
		System.out.println("系统服务启动成功" + env);
		// swagger-ui.html 地址
		System.out.println("http://localhost:"+env.getProperty("server.port")+"/swagger-ui.html");
	}

}
