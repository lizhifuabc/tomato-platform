package com.tomato.goods;

import com.tomato.goods.domain.entity.SeckillGoodsEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
    @Resource
    DefaultRedisScript<Long> seckillRedisScript;
    @Test
    public void test(){
        SeckillGoodsEntity seckillGoodsEntity = new SeckillGoodsEntity();
        seckillGoodsEntity.setGoodsId(10000L);
        seckillGoodsEntity.setCreateTime(LocalDateTime.now());
        redisTemplate.opsForValue().set("test", seckillGoodsEntity);
        Object test = redisTemplate.opsForValue().get("test");
        System.out.println(test);
    }
    @Test
    public void seckill(){
        List<String> redisKeys = Arrays.asList("123456");
        Long result = (Long) redisTemplate.execute(seckillRedisScript, redisKeys);
        if(result > 0){
            System.out.println("剩余库存:"+result);
        }
        System.out.println(result);
    }
    @Test
    public void lua(){
        StringBuilder lua = new StringBuilder();
        lua.append("if (redis.call('exists', KEYS[1]) == 1) then");
        lua.append("    local stock = tonumber(redis.call('get', KEYS[1]));");
        lua.append("    if (stock == -1) then");
        lua.append("        return 1;");
        lua.append("    end;");
        lua.append("    if (stock > 0) then");
        lua.append("        redis.call('incrby', KEYS[1], -1);");
        lua.append("        return stock;");
        lua.append("    end;");
        lua.append("    return 0;");
        lua.append("end;");
        lua.append("return -1;");
        System.out.println(lua.toString());
    }
}
