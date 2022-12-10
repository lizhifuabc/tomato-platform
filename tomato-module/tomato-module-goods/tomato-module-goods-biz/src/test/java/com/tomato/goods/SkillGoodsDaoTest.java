package com.tomato.goods;

import com.tomato.goods.skill.dao.SkillGoodsDao;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SkillGoodsDao
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@SpringBootTest
public class SkillGoodsDaoTest {
    @Resource
    SkillGoodsDao skillGoodsDao;

    @Test
    public void test(){
        int i = skillGoodsDao.updateSkillRemaining(1L, 1);
        System.out.println(i);
    }
}
