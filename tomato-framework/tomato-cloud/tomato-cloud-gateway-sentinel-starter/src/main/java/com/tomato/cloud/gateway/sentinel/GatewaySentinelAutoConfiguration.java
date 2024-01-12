package com.tomato.cloud.gateway.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.tomato.common.domain.resp.Resp;
import com.tomato.jackson.utils.JacksonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Sentinel Spring Cloud Gateway 支持
 *
 * @author lizhifu
 * @since 2023/7/18
 */
@AutoConfiguration
@Slf4j
public class GatewaySentinelAutoConfiguration {

	@PostConstruct
	public void init() {
		log.info("tomato-cloud-sentinel-gateway-starter 自动装配");
	}

	/**
	 * 限流、熔断统一处理类 classpath中存在WebFlux的ServerResponse类时才启用该配置 只在WebFlux环境生效
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(ServerResponse.class)
	public static class WebfluxHandler {

		@Bean
		public BlockRequestHandler webfluxBlockExceptionHandler() {
			log.info("tomato-cloud-sentinel-gateway-starter 自动装配|WebFlux环境");
			return (exchange, t) ->
			// 设置状态码：429，请求太多
			ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(Resp.buildFailure(t.getMessage())));
		}

	}

	/**
	 * 限流、熔断统一处理类 只有在classpath中有HttpServletRequest类的情况下,才会实例化这个配置类和其中的@Bean。 只有在Spring
	 * MVC环境下才生效
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(HttpServletRequest.class)
	public static class WebmvcHandler {

		@Bean
		public BlockExceptionHandler webmvcBlockExceptionHandler() {
			log.info("tomato-cloud-sentinel-gateway-starter 自动装配|Spring MVC环境");
			return (request, response, e) -> {
				// 设置状态码：429，请求太多
				response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
				Resp<String> result = Resp.buildFailure("请求太多，请稍后重试。");
				response.getWriter().print(JacksonUtils.toJson(result));
			};
		}

	}

}
