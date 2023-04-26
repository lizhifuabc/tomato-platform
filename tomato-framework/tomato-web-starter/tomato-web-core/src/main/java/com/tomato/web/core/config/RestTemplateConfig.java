package com.tomato.web.core.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate
 *
 * @author lizhifu
 * @since 2023/1/6
 */
@AutoConfiguration
@Slf4j
public class RestTemplateConfig {
    @PostConstruct
    public void postConstruct() {
        log.info("tomato-web-starter RestTemplateConfig Auto Configure.");
    }
    /**
     * 默认使用JDK 自带的HttpURLConnection作为底层实现
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
