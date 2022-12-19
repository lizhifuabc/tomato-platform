package com.tomato.goods.seckill.service;

import com.tomato.domain.exception.BusinessException;
import com.tomato.goods.domain.entity.SeckillActivityEntity;
import com.tomato.goods.domain.entity.SeckillGoodsEntity;
import com.tomato.goods.domain.entity.SeckillUserEntity;
import com.tomato.goods.seckill.dao.SeckillActivityDao;
import com.tomato.goods.seckill.dao.SeckillGoodsDao;
import com.tomato.goods.seckill.dao.SeckillUserDao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 抢购校验
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Service
public class SeckillCheckService {
    private final SeckillActivityDao seckillActivityDao;
    private final SeckillGoodsDao seckillGoodsDao;
    private final SeckillUserDao seckillUserDao;
    public SeckillCheckService(SeckillActivityDao seckillActivityDao, SeckillGoodsDao seckillGoodsDao, SeckillUserDao seckillUserDao) {
        this.seckillActivityDao = seckillActivityDao;
        this.seckillGoodsDao = seckillGoodsDao;
        this.seckillUserDao = seckillUserDao;
    }

    public SeckillActivityEntity checkSeckillActivity(Long seckillActivityId){
        SeckillActivityEntity seckillActivity = seckillActivityDao.selectById(seckillActivityId);
        if (null == seckillActivity){
            throw new BusinessException("活动不存在");
        }
        if(seckillActivity.getDisabledFlag()){
            throw new BusinessException("活动已关闭");
        }
        if(seckillActivity.getStartTime().isAfter(LocalDateTime.now())){
            throw new BusinessException("活动尚未开始");
        }
        if(seckillActivity.getEndTime().isBefore(LocalDateTime.now())){
            throw new BusinessException("活动尚已经结束");
        }
        return seckillActivity;
    }

    public SeckillGoodsEntity checkSeckillGoods(Long seckillGoodsId){
        SeckillGoodsEntity seckillGoods = seckillGoodsDao.selectById(seckillGoodsId);
        if (null == seckillGoods){
            throw new BusinessException("商品不存在");
        }
        if(seckillGoods.getSeckillRemaining() <= 0){
            throw new BusinessException("库存不足");
        }
        return seckillGoods;
    }

    public void checkSeckillUser(Long userId,Long seckillGoodsId){
        SeckillUserEntity seckillGoods = seckillUserDao.selectByUserIdSeckillGoodsId(userId,seckillGoodsId);
        if (null == seckillGoods){
            return;
        }
        if(seckillGoods.getSeckillRemaining() <= 0){
            throw new BusinessException("已到达最大抢购次数");
        }
    }
}
