package com.tomato.seckill.manager;

import com.tomato.common.exception.BusinessException;
import com.tomato.seckill.dao.SeckillUserDao;
import com.tomato.seckill.dao.SeckillUserDetailDao;
import com.tomato.seckill.domain.bo.UpdateSeckillRemainingBO;
import com.tomato.seckill.domain.entity.SeckillGoodsEntity;
import com.tomato.seckill.domain.entity.SeckillUserDetailEntity;
import com.tomato.seckill.domain.entity.SeckillUserEntity;
import org.springframework.stereotype.Service;

/**
 * 用户参与活动
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Service
public class SeckillUserManager {

	private final SeckillUserDetailDao seckillUserDetailDao;

	private final SeckillUserDao seckillUserDao;

	public SeckillUserManager(SeckillUserDetailDao seckillUserDetailDao, SeckillUserDao seckillUserDao) {
		this.seckillUserDetailDao = seckillUserDetailDao;
		this.seckillUserDao = seckillUserDao;
	}

	public void userSeckill(SeckillGoodsEntity seckillGoodsEntity, Long userId) {
		Long seckillUserId;
		SeckillUserEntity seckillUserEntity = seckillUserDao.selectByUserIdSeckillGoodsId(userId,
				seckillGoodsEntity.getId());
		if (null == seckillUserEntity) {
			seckillUserEntity = new SeckillUserEntity();
			seckillUserEntity.setSeckillGoodsId(seckillGoodsEntity.getId());
			seckillUserEntity.setUserId(userId);
			seckillUserEntity.setGoodsId(seckillGoodsEntity.getGoodsId());
			seckillUserEntity.setSeckillCount(seckillGoodsEntity.getSeckillLimit());
			// 剩余 -1
			seckillUserEntity.setSeckillRemaining(seckillGoodsEntity.getSeckillLimit() - 1);
			seckillUserId = seckillUserDao.insert(seckillUserEntity);
		}
		else {
			seckillUserId = seckillUserEntity.getId();
			UpdateSeckillRemainingBO updateSkillRemainingBO = UpdateSeckillRemainingBO.builder()
				.seckillGoodsId(seckillUserEntity.getSeckillGoodsId())
				.version(seckillUserEntity.getVersion())
				.userId(seckillUserEntity.getUserId())
				.build();
			int res = seckillUserDao.updateSkillRemaining(updateSkillRemainingBO);
			if (res != 1) {
				throw new BusinessException("用户已到达最大抢购次数");
			}
		}
		// 只做记录使用
		SeckillUserDetailEntity seckillUserDetailEntity = new SeckillUserDetailEntity();
		seckillUserDetailEntity.setSeckillUserId(seckillUserId);
		seckillUserDetailDao.insert(seckillUserDetailEntity);
	}

}
