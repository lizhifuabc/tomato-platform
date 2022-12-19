package com.tomato.goods;

import com.tomato.goods.domain.entity.SeckillUserEntity;
import com.tomato.goods.seckill.dao.SeckillUserDao;
import com.tomato.goods.seckill.domain.bo.UpdateSeckillRemainingBO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SkillUserDao
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@SpringBootTest
public class SeckillUserDaoTest {
    @Resource
    SeckillUserDao seckillUserDao;

    @Test
    public void insert(){
        SeckillUserEntity seckillUserEntity = new SeckillUserEntity();
        seckillUserEntity.setSeckillGoodsId(1L);
        seckillUserEntity.setUserId(1L);
        seckillUserEntity.setSeckillCount(1);
        seckillUserEntity.setGoodsId(1L);
        seckillUserEntity.setSeckillRemaining(1);
        seckillUserDao.insert(seckillUserEntity);
    }
    @Test
    public void updateSkillRemaining(){
        UpdateSeckillRemainingBO updateSkillRemainingBO =  UpdateSeckillRemainingBO.builder()
                .seckillGoodsId(1L)
                .userId(1L)
                .version(1)
                .build();
        System.out.println(seckillUserDao.updateSkillRemaining(updateSkillRemainingBO));
    }
}
