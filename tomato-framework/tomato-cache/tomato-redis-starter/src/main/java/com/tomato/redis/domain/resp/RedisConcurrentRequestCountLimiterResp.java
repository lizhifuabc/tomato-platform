package com.tomato.redis.domain.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedisConcurrentRequestCountLimiterResp {

	/**
	 * 是否允许
	 */
	private boolean allowed;

	/**
	 * 已使用
	 */
	private Long tokensUsed;

}
