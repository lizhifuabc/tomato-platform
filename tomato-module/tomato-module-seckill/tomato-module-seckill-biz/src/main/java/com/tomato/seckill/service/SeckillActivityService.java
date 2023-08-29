package com.tomato.seckill.service;

import com.tomato.seckill.dao.SeckillActivityDao;
import com.tomato.seckill.domain.entity.SeckillActivityEntity;
import com.tomato.seckill.domain.req.SeckillActivityCreateReq;
import com.tomato.web.core.util.BeanUtil;
import org.springframework.stereotype.Service;

/**
 * 创建秒杀活动
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Service
public class SeckillActivityService {

	private final SeckillActivityDao seckillActivityDao;

	public SeckillActivityService(SeckillActivityDao seckillActivityDao) {
		this.seckillActivityDao = seckillActivityDao;
	}

	public void create(SeckillActivityCreateReq seckillActivityCreateReq) {
		SeckillActivityEntity seckillActivity = BeanUtil.copy(seckillActivityCreateReq, SeckillActivityEntity.class);
		seckillActivityDao.insert(seckillActivity);
	}

}
