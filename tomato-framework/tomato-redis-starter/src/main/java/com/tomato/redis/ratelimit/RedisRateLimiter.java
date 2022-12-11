package com.tomato.redis.ratelimit;

import com.tomato.domain.resp.Resp;
import com.tomato.domain.resp.SingleResp;
import com.tomato.redis.domain.req.RedisRateLimiterReq;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 * redis 限流组件
 * replenishRate = burstCapacity ，可以实现稳定的速率。
 * burstCapacity > replenishRate，允许临时突发。速率限制器需要在两次突发之间保留一段时间（根据replenishRate），
 * 因为两个连续的突发将导致请求丢弃（HTTP 429-太多请求）
 * @author lizhifu
 * @date 2022/12/10
 */
public class RedisRateLimiter {
    private StringRedisTemplate stringRedisTemplate;
    private RedisScript<List<Long>> script;

    public RedisRateLimiter(StringRedisTemplate stringRedisTemplate, RedisScript<List<Long>> script) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.script = script;
    }

    public SingleResp isAllowed(RedisRateLimiterReq redisRateLimiterReq){
        // 用户每秒允许多少个请求，而没有任何丢弃的请求。这是令牌桶被填充的速率。
        int replenishRate = redisRateLimiterReq.getReplenishRate();
        // 允许用户在一秒钟内执行的最大请求数。这是令牌桶可以容纳的令牌数。将此值设置为零将阻止所有请求。
        int burstCapacity = redisRateLimiterReq.getBurstCapacity();
        // 一个请求要花费多少个令牌。这是每个请求从存储桶中获取的令牌数，默认为1。
        int requestedTokens = redisRateLimiterReq.getRequestedTokens();
        // 设置 keys
        List<String> keys = getKeys(redisRateLimiterReq.getId());
        // LUA脚本的参数。time（）以秒为单位返回unixtime。
        List<String> scriptArgs = Arrays.asList(replenishRate + "", burstCapacity + "", "", requestedTokens + "");
        // allowed, tokens_left = redis.eval(SCRIPT, keys, args)
        List<Long> results = this.stringRedisTemplate.execute(this.script, keys, replenishRate + "",burstCapacity + "","", requestedTokens + "");
        boolean allowed = results.get(0) == 1L;
        Long tokensLeft = results.get(1);
        return SingleResp.buildSuccess();
    }
    private List<String> getKeys(String id) {
        // redis 集群的时候 `{}` 包裹的内容会被哈希到同一个哈希槽中
        // 唯一标识
        String prefix = "request_rate_limiter.{" + id;
        // 令牌桶的两个 key
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }
}
