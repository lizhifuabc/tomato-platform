package com.tomato.redis.domain.req;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * redis 令牌桶限流
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class RedisRateLimiterReq {
    /**
     * 限流唯一性标识
     */
    private String id;
    @Min(1)
    private int replenishRate;

    @Min(0)
    private int burstCapacity = 1;

    @Min(1)
    private int requestedTokens = 1;
}
