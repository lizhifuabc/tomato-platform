package com.tomato.goods;

import com.tomato.goods.domain.entity.SeckillGoodsEntity;
import com.tomato.goods.seckill.dao.SeckillGoodsDao;
import com.tomato.goods.seckill.manager.SeckillUserManager;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SeckillUserManager
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@SpringBootTest
public class SeckillUserManagerTest {
    @Resource
    SeckillUserManager seckillUserManager;
    @Resource
    SeckillGoodsDao seckillGoodsDao;
    @Test
    public void test(){
        SeckillGoodsEntity seckillGoodsEntity = seckillGoodsDao.selectById(1L);
        seckillUserManager.userSeckill(seckillGoodsEntity,10001L);
    }
}
