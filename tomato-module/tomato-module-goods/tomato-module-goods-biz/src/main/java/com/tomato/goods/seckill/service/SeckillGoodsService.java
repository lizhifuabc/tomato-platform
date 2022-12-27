package com.tomato.goods.seckill.service;

import com.tomato.domain.exception.BusinessException;
import com.tomato.goods.domain.entity.SeckillGoodsEntity;
import com.tomato.goods.seckill.dao.SeckillGoodsDao;
import com.tomato.goods.seckill.manager.SeckillUserManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @since 2022/12/27
 */
@Service
public class SeckillGoodsService {
    private final SeckillGoodsDao seckillGoodsDao;
    private final SeckillUserManager seckillUserManager;

    public SeckillGoodsService(SeckillGoodsDao seckillGoodsDao, SeckillUserManager seckillUserManager) {
        this.seckillGoodsDao = seckillGoodsDao;
        this.seckillUserManager = seckillUserManager;
    }

    /**
     * 扣减库存
     */
    @Transactional(rollbackFor = Exception.class)
    public void deductSeckillGoods(Long seckillGoodsId,Long userId){
        SeckillGoodsEntity seckillGoodsEntity = seckillGoodsDao.selectById(seckillGoodsId);
        int skillRemainingRes = seckillGoodsDao.updateSkillRemaining(seckillGoodsEntity.getId(), seckillGoodsEntity.getVersion());
        if(skillRemainingRes != 1){
            throw new BusinessException("抢购失败，库存不足");
        }
        seckillUserManager.userSeckill(seckillGoodsEntity,userId);
    }
}
