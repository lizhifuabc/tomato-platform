package com.tomato.kill;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀启动类
 *
 * @author lizhifu
 * @since 2023/3/9
 */
@SpringBootApplication
@EnableDiscoveryClient
public class KillApplication {
    @Value("${spring.autoconfigure.exclude:}")
    private String serverAddr;
    public static void main(String[] args) {
        SpringApplication.run(KillApplication.class, args);
        System.out.println("KillApplication is success!");
    }
    @RestController
    public class EchoController {
        @GetMapping(value = "/echo/{string}")
        public String echo(@PathVariable String string) {
            return "Hello Nacos Discovery " + string+":serverAddr:"+serverAddr;
        }
    }
}
