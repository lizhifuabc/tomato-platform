package com.tomato.notice;

import com.tomato.common.exception.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * WebClient
 *
 * @author lizhifu
 * @since 2023/4/24
 */
public class WebClientTest {
    public static WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader(HttpHeaders.ACCEPT_CHARSET, "UTF-8")
                .build();
    public static void main(String[] args) throws InterruptedException {
        test();
    }

    public static void test() throws InterruptedException {
        webClient.post()
                .uri("http://localhost:9800/receive")
                .body(BodyInserters.fromValue("{\"m\": \"12334523\", \"hmac\": \"12334523\"}"))
                // 获取响应体
                .retrieve()
                //响应数据类型转换
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(1))
                .doOnError(throwable -> {
                    System.out.println("发送通知失败"+throwable.getMessage());
                }).subscribe(result -> {
                    System.out.println("result");
                    System.out.println(result);
                });

        Thread.sleep(5000);
    }
}
