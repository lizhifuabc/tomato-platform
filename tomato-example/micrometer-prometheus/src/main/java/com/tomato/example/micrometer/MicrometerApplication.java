package com.tomato.example.micrometer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * MicrometerApplication
 *
 * @author lizhifu
 * @since 2023/4/25
 */
@SpringBootApplication
public class MicrometerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MicrometerApplication.class, args);
        Environment env = applicationContext.getEnvironment();
        System.out.println("系统服务启动成功" + env);
        // swagger-ui.html 地址
        System.out.println("http://localhost:"+env.getProperty("server.port")+"/actuator/prometheus");
    }
}
