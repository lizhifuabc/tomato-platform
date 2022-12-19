package com.tomato.redis.ratelimit;

import com.tomato.domain.resp.SingleResp;
import com.tomato.redis.domain.req.RedisConcurrentRequestCountLimiterReq;
import com.tomato.redis.domain.resp.RedisConcurrentRequestCountLimiterResp;
import com.tomato.redis.domain.resp.RedisRateLimiterResp;
import jakarta.validation.Valid;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

/**
 * 请求次数限制
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Validated
public class RedisConcurrentRequestCountLimiter {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisScript<Long> script;

    public RedisConcurrentRequestCountLimiter(StringRedisTemplate stringRedisTemplate, RedisScript<Long> script) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.script = script;
    }
    public SingleResp<RedisConcurrentRequestCountLimiterResp> isAllowed(@Valid RedisConcurrentRequestCountLimiterReq req){
        // 设置 keys
        List<String> keys = getKeys(req.getId());
        Long results = this.stringRedisTemplate.execute(this.script, keys, req.getCount() + "", req.getInterval() + "");

        RedisConcurrentRequestCountLimiterResp resp = RedisConcurrentRequestCountLimiterResp.builder()
                .allowed(results >= 1L)
                .tokensUsed(results)
                .build();
        return SingleResp.of(resp);
    }
    private List<String> getKeys(String id) {
        // redis 集群的时候 `{}` 包裹的内容会被哈希到同一个哈希槽中
        // 唯一标识
        String prefix = "concurrent_request_count_limiter.{" + id;
        // 令牌桶的两个 key
        String tokenKey = prefix + "}.tokens";
        return Arrays.asList(tokenKey);
    }
}
