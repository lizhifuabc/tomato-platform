package com.tomato.seckill.controller;

import com.tomato.seckill.constant.RedisConstant;
import com.tomato.seckill.domain.req.SeckillUserReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 用户抢购
 *
 * @author lizhifu
 * @since 2023/3/24
 */
@RestController
@Slf4j
@Tag(name = "用户抢购接口")
public class SeckillUserController {
    private final DefaultRedisScript<Long> userSeckillRedisScript;

    private final RedisTemplate redisTemplate;

    public SeckillUserController(DefaultRedisScript<Long> userSeckillRedisScript, RedisTemplate redisTemplate) {
        this.userSeckillRedisScript = userSeckillRedisScript;
        this.redisTemplate = redisTemplate;
    }


    /**
     * 秒杀
     * @param seckillUserReq 秒杀请求
     */
    @PostMapping("/seckill/user")
    public void seckill(@RequestBody @Valid SeckillUserReq seckillUserReq){

        String goodsKey = RedisConstant.SECKILL_GOODS_SECKILL + seckillUserReq.getSeckillGoodsId();
        String goodsUserKey = RedisConstant.SECKILL_GOODS_SECKILL_USER + seckillUserReq.getSeckillGoodsId();
        log.info("用户抢购请求,seckillUserReq:{},goodsKey:{},goodsUserKey:{}",goodsKey,goodsUserKey,seckillUserReq);

        List<String> keys = Arrays.asList(goodsKey, goodsUserKey);
        Long result = (Long) redisTemplate.execute(userSeckillRedisScript, keys, seckillUserReq.getUserId());
        log.info("用户{}抢购结果,result:{}",seckillUserReq,result);
    }
}
