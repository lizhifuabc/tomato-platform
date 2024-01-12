package com.tomato.cloud.feign.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.tomato.common.domain.resp.Resp;
import com.tomato.jackson.utils.JacksonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Sentinel Spring Cloud feign 支持
 *
 * @author lizhifu
 * @since 2023/7/21
 */
@AutoConfiguration
public class FeignSentinelAutoConfiguration {

	@PostConstruct
	public void init() {
		System.out.println("tomato-cloud-sentinel-feign-starter 自动装配");
	}

	/**
	 * 限流、熔断统一处理类
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(HttpServletRequest.class)
	public static class WebmvcHandler {

		@Bean
		public BlockExceptionHandler webmvcBlockExceptionHandler() {
			return (request, response, e) -> {
				response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
				Resp<String> result = Resp.buildFailure("请求太多，请稍后重试.");
				response.getWriter().print(JacksonUtils.toJson(result));
			};
		}

	}

	/**
	 * 限流、熔断统一处理类
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(ServerResponse.class)
	public static class WebfluxHandler {

		@Bean
		public BlockRequestHandler webfluxBlockExceptionHandler() {
			return (exchange, t) -> ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(Resp.buildFailure(t.getMessage())));
		}

	}

}
