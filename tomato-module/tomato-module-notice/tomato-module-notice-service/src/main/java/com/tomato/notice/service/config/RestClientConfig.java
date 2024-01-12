package com.tomato.notice.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;


/**
 * RestClient 配置
 *
 * @author lizhifu
 * @since 2024/1/12
 */
@Configuration
public class RestClientConfig {

	@Bean
	public RestClient restClient() {
        return RestClient.builder()
				.requestFactory(new HttpComponentsClientHttpRequestFactory())
				.defaultHeader("Accept", "application/json")
				.build();
	}
}
