package com.tomato.redis.domain.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * 请求次数限制
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedisConcurrentRequestCountLimiterReq {

	/**
	 * 限流唯一性标识
	 */
	@NotBlank
	private String id;

	/**
	 * 数量
	 */
	@NotNull
	@Min(1)
	private Integer count;

	/**
	 * 时间间隔,单位 秒
	 */
	@Min(1)
	@NotNull
	private Integer interval;

}
