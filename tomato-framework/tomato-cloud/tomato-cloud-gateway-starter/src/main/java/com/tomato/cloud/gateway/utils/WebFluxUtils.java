package com.tomato.cloud.gateway.utils;

import com.tomato.common.resp.Resp;
import com.tomato.jackson.utils.JacksonUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 常用 WebFlux 工具类
 *
 * @author lizhifu
 * @since 2023/6/4
 */
public class WebFluxUtils {

	/**
	 * 写入 JSON 响应
	 * @param response 响应
	 * @param result 结果
	 * @return Mono<Void>
	 */
	public static Mono<Void> writeJsonResponse(ServerHttpResponse response, Resp<String> result) {
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		response.setStatusCode(HttpStatus.valueOf(result.getStatus()));

		String jsonResult = JacksonUtils.toJson(result);
		byte[] bytes = jsonResult.getBytes(StandardCharsets.UTF_8);

		DataBuffer buffer = response.bufferFactory().wrap(bytes);
		return response.writeWith(Flux.just(buffer));
	}

}
