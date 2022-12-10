package com.tomato.goods;

import com.tomato.goods.entity.SkillUserEntity;
import com.tomato.goods.skill.dao.SkillUserDao;
import com.tomato.goods.skill.domain.bo.UpdateSkillRemainingBO;
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
public class SkillUserDaoTest {
    @Resource
    SkillUserDao skillUserDao;

    @Test
    public void insert(){
        SkillUserEntity skillUserEntity = new SkillUserEntity();
        skillUserEntity.setSkillGoodsId(1L);
        skillUserEntity.setUserId(1L);
        skillUserEntity.setSkillCount(1);
        skillUserEntity.setGoodsId(1L);
        skillUserEntity.setSkillRemaining(1);
        skillUserDao.insert(skillUserEntity);
    }
    @Test
    public void updateSkillRemaining(){
        UpdateSkillRemainingBO updateSkillRemainingBO =  UpdateSkillRemainingBO.builder()
                .skillGoodsId(1L)
                .userId(1L)
                .version(1)
                .build();
        System.out.println(skillUserDao.updateSkillRemaining(updateSkillRemainingBO));
    }
}
