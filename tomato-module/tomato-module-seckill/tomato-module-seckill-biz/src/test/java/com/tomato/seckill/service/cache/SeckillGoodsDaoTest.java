package com.tomato.seckill.service.cache;

import com.tomato.seckill.dao.SeckillGoodsDao;
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
public class SeckillGoodsDaoTest {

	@Resource
	SeckillGoodsDao seckillGoodsDao;

	@Test
	public void test() {
		int i = seckillGoodsDao.updateSkillRemaining(1L, 1);
		System.out.println(i);
	}

}
