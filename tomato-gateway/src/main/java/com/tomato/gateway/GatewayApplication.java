package com.tomato.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网关启动程序
 *
 * @author lizhifu
 * @since 2023/3/9
 */
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        System.out.println("网关启动成功");
    }
}
