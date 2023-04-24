package com.tomato.notice.config;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

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
        // TODO 2.配置重试次数 3.配置重试间隔
        //  4.配置重试条件 5.配置重试回调 6.配置重试异常 7.配置重试异常回调 8.配置重试异常处理
        //  9.配置重试异常处理回调 10.配置重试异常处理回调 11.配置重试异常处理回调
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofSeconds(5))
                ;
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultRequest(request -> request
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                )
                .build();
    }
}
