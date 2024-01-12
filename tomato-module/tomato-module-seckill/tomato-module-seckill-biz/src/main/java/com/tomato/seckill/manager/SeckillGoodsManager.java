package com.tomato.seckill.manager;

import com.tomato.common.domain.resp.Resp;
import com.tomato.seckill.dao.SeckillGoodsDao;
import com.tomato.seckill.domain.entity.SeckillGoodsEntity;
import com.tomato.seckill.domain.req.SeckillGoodsCreateListReq;
import com.tomato.web.core.util.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Service
public class SeckillGoodsManager {

	private final SeckillGoodsDao seckillGoodsDao;

	public SeckillGoodsManager(SeckillGoodsDao seckillGoodsDao) {
		this.seckillGoodsDao = seckillGoodsDao;
	}

	public Resp goodsCreate(SeckillGoodsCreateListReq listReq) {
		List<SeckillGoodsEntity> goodsList = new ArrayList<>();
		listReq.getGoodsList().forEach(seckillGoodsCreateReq -> {
			SeckillGoodsEntity seckillGoodsEntity = BeanUtil.copy(seckillGoodsCreateReq, SeckillGoodsEntity.class);
			seckillGoodsEntity.setSeckillActivityId(listReq.getSeckillActivityId());
			seckillGoodsEntity.setSeckillRemaining(seckillGoodsCreateReq.getSeckillCount());
			goodsList.add(seckillGoodsEntity);
		});
		seckillGoodsDao.insertList(goodsList);
		return Resp.buildSuccess();
	}

}
