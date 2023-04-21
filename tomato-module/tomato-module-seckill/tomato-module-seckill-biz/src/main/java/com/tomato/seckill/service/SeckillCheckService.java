package com.tomato.seckill.service;

import com.tomato.common.exception.BusinessException;
import com.tomato.seckill.dao.SeckillActivityDao;
import com.tomato.seckill.dao.SeckillGoodsDao;
import com.tomato.seckill.dao.SeckillUserDao;
import com.tomato.seckill.domain.entity.SeckillActivityEntity;
import com.tomato.seckill.domain.entity.SeckillGoodsEntity;
import com.tomato.seckill.domain.entity.SeckillUserEntity;
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

    /**
     * 活动校验
     * @param seckillActivityId 活动id
     * @return
     */
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

    /**
     * 数据库库存校验
     * @param seckillGoodsId 秒杀活动商品记录id
     * @return
     */
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

    /**
     * 用户抢购次数校验
     * @param userId 用户ID
     * @param seckillGoodsId 秒杀活动商品记录id
     */
    public void checkSeckillUser(Long userId,Long seckillGoodsId){
        SeckillUserEntity seckillGoods = seckillUserDao.selectByUserIdSeckillGoodsId(userId,seckillGoodsId);
        if (null == seckillGoods){
            return;
        }
        if(seckillGoods.getSeckillRemaining() <= 0){
            throw new BusinessException("用户已到达最大抢购次数");
        }
    }
}
