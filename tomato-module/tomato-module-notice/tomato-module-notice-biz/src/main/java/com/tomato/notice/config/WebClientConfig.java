package com.tomato.notice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient 配置:非阻塞式客户端
 *
 * @author lizhifu
 * @since 2023/4/24
 */
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        // TODO 1.配置超时时间 2.配置重试次数 3.配置重试间隔
        //  4.配置重试条件 5.配置重试回调 6.配置重试异常 7.配置重试异常回调 8.配置重试异常处理
        //  9.配置重试异常处理回调 10.配置重试异常处理回调 11.配置重试异常处理回调
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader(HttpHeaders.ACCEPT_CHARSET, "UTF-8")
                .build();
    }
}
