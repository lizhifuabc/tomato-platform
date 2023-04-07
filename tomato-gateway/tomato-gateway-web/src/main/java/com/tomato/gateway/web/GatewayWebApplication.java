package com.tomato.gateway.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 网关启动程序
 *
 * @author lizhifu
 * @since 2023/3/9
 */
@Slf4j
@SpringBootApplication
public class GatewayWebApplication {
    public static void main(String[] args) {
        log.info("Begin to start Spring Boot Application");
        long startTime = System.currentTimeMillis();
        ConfigurableApplicationContext applicationContext = SpringApplication.run(GatewayWebApplication.class, args);
        System.out.println("网关启动成功");
        long endTime = System.currentTimeMillis();
        log.info("End starting Spring Boot Application, Time used: "+ (endTime - startTime) );


        Environment env = applicationContext.getEnvironment();
        log.info("环境信息{}",env);
    }
}
