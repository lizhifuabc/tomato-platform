package com.tomato.channel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 启动类
 * @author lizhifu
 */
@EnableDiscoveryClient
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "渠道服务 API", version = "1.0-SNAPSHOT", description = "渠道服务 Information"),
		servers = {@Server(url = "http://localhost:9991/")})
public class ChannelApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(ChannelApplication.class, args);
		Environment env = applicationContext.getEnvironment();
		System.out.println("系统服务启动成功" + env);
		// swagger-ui.html 地址
		System.out.println("http://localhost:"+env.getProperty("server.port")+"/swagger-ui.html");
	}

}
