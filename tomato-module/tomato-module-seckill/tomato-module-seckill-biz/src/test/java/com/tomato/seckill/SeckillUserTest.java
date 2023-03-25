package com.tomato.seckill;

import com.tomato.seckill.constant.RedisConstant;
import com.tomato.seckill.domain.req.SeckillUserReq;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Arrays;
import java.util.List;

/**
 * SeckillUserTest
 *
 * @author lizhifu
 * @since 2023/3/24
 */
@SpringBootTest
@Slf4j
public class SeckillUserTest {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    DefaultRedisScript<Long> userSeckillRedisScript;
    @Test
    public void test(){
        SeckillUserReq seckillUserReq = new SeckillUserReq();
        seckillUserReq.setSeckillGoodsId(1L);
        seckillUserReq.setUserId(1L);

        System.out.println("test");
        log.info("用户抢购请求,seckillUserReq:{}",seckillUserReq);
        String goods = RedisConstant.SECKILL_GOODS_SECKILL + seckillUserReq.getSeckillGoodsId();
        String goodsUser = RedisConstant.SECKILL_GOODS_SECKILL_USER + seckillUserReq.getSeckillGoodsId();

        log.info("goods:{}",goods);
        log.info("goodsUser:{}",goodsUser);

        List<String> keys = Arrays.asList(goods, goodsUser);
        Long result = (Long) redisTemplate.execute(userSeckillRedisScript, keys, seckillUserReq.getUserId());
        log.info("用户{}抢购结果,result:{}",seckillUserReq,result);
    }
}
