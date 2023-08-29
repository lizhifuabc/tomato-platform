package com.tomato.redis.domain.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * redis 限流
 *
 * @author lizhifu
 * @date 2022/12/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedisRateLimiterResp {

	private boolean allowed;

	private Long tokensLeft;

}
