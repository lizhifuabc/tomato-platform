package com.tomato.redis.domain.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * redis 限流
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedisRateLimiterReq {

	/**
	 * 限流唯一性标识
	 */
	@NotBlank
	private String id;

	/**
	 * 令牌添加速率
	 */
	@Min(1)
	private int replenishRate;

	/**
	 * 桶的容量
	 */
	@Min(0)
	private int burstCapacity = 1;

	/**
	 * 每个请求需要的令牌数量
	 */
	@Min(1)
	private int requestedTokens = 1;

}
