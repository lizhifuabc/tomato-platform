package com.tomato.seckill.dao;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/3/18
 */
@SpringBootTest
public class SeckillActivityDaoTest {
    @Resource
    private SeckillActivityDao seckillActivityDao;

    @Test
    public void test(){
        seckillActivityDao.selectByTime(null,null);
    }
}
