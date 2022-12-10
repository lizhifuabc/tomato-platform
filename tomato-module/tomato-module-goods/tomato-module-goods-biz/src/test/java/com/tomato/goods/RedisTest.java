package com.tomato.goods;

import com.tomato.goods.entity.SkillGoodsEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;

/**
 * RedisTest
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@SpringBootTest
public class RedisTest {
    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void test(){
        SkillGoodsEntity skillGoodsEntity = new SkillGoodsEntity();
        skillGoodsEntity.setGoodsId(10000L);
        skillGoodsEntity.setCreateTime(LocalDateTime.now());
        redisTemplate.opsForValue().set("test",skillGoodsEntity);
        Object test = redisTemplate.opsForValue().get("test");
        System.out.println(test);
    }
}
