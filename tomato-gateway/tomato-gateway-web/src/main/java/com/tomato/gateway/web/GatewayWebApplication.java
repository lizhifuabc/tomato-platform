package com.tomato.gateway.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Hooks;

/**
 * 网关启动程序
 *
 * @author lizhifu
 * @since 2023/3/9
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
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

        // 为了在WebFlux中使用MDC，我们需要启用自动上下文传播
        // https://github.com/micrometer-metrics/tracing/wiki/Spring-Cloud-Sleuth-3.1-Migration-Guide
        // https://www.saoniuhuo.com/question/detail-2515079.html
        // TODO Reactor 3.5.3 之后不需要这个了，但是目前还是需要的
        Hooks.enableAutomaticContextPropagation();
    }
}
