package com.tomato.seckill.service.cache;

import com.tomato.seckill.service.SeckillCheckService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SeckillCheckService
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@SpringBootTest
public class SeckillCheckServiceTest {
    @Resource
    SeckillCheckService seckillCheckService;

    @Test
    public void test(){
        seckillCheckService.checkSeckillActivity(1L);
        seckillCheckService.checkSeckillUser(1L,1L);
        seckillCheckService.checkSeckillGoods(1L);
    }
}
