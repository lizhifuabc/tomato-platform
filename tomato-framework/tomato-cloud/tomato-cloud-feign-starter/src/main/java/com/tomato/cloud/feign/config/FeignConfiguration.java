package com.tomato.cloud.feign.config;

import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.micrometer.MicrometerCapability;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 开启日志
 *
 * @author lizhifu
 * @since 2023/6/6
 */
@Configuration
@Slf4j
public class FeignConfiguration {

	/**
	 * 全局配置日志
	 * <p>
	 * 1. NONE（默认） --- 不记录任何日志 2. BASIC --- 仅记录请求方法，URL，响应状态代码以及执行时间（适合生产环境） 3. HEADERS
	 * --- 记录BASIC级别的基础上，记录请求和响应的header 4. FULL --- 记录请求和响应header，body和元数据
	 * @return Logger.Level 日志级别
	 */
	@Bean
	Logger.Level feignLoggerLevel() {
		log.info("tomato-cloud-feign-starter Logger.Level 自动配置");
		return Logger.Level.BASIC;
	}

	/**
	 * 设置连接超时时间
	 * @return Request.Options 超时配置
	 */
	@Bean
	public Request.Options options() {
		log.info("tomato-cloud-feign-starter Request.Options 自动配置");
		// connectTimeoutMillis: 连接超时时间
		return new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true);
	}

	/**
	 * 重试次数
	 * @return Retryer 重试器
	 */
	@Bean
	public Retryer feignRetryer() {
		log.info("tomato-cloud-feign-starter Retryer 自动配置");
		return new Retryer.Default();
	}

	@Bean
	public Feign.Builder feignBuilder() {
		log.info("tomato-cloud-feign-starter Feign.Builder 自动配置");
		return Feign.builder()
			// 添加 MicrometerCapability
			.addCapability(new MicrometerCapability());
	}

}
