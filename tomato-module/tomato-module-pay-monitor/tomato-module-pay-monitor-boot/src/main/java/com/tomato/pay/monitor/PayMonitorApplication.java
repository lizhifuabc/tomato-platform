package com.tomato.pay.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author lizhifu
 */
@SpringBootApplication
public class PayMonitorApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(PayMonitorApplication.class, args);
        Environment env = applicationContext.getEnvironment();
        System.out.println("系统服务启动成功" + env);
        // swagger-ui.html 地址
        System.out.println("http://localhost:"+env.getProperty("server.port")+"/swagger-ui.html");
    }
}
