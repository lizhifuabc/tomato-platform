package com.tomato.seckill.strategy;

import com.tomato.domain.exception.BusinessException;
import com.tomato.seckill.dao.SeckillGoodsDao;
import com.tomato.seckill.domain.entity.SeckillGoodsEntity;
import com.tomato.seckill.manager.SeckillUserManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据库实现
 *
 * @author lizhifu
 * @since 2023/3/17
 */
@Service("daoSeckillStrategy")
public class DaoSeckillStrategy implements SeckillStrategyFactory{
    private final SeckillGoodsDao seckillGoodsDao;
    private final SeckillUserManager seckillUserManager;

    public DaoSeckillStrategy(SeckillGoodsDao seckillGoodsDao, SeckillUserManager seckillUserManager) {
        this.seckillGoodsDao = seckillGoodsDao;
        this.seckillUserManager = seckillUserManager;
    }

    @Override
    public void checkSeckillGoods(Long seckillGoodsId, Long seckillActivityId) {

    }

    @Override
    public void checkUserLimit(Long userId, Long seckillGoodsId) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deductSeckillGoods(Long seckillGoodsId, Long userId) {
        SeckillGoodsEntity seckillGoodsEntity = seckillGoodsDao.selectById(seckillGoodsId);
        int skillRemainingRes = seckillGoodsDao.updateSkillRemaining(seckillGoodsEntity.getId(), seckillGoodsEntity.getVersion());
        if(skillRemainingRes != 1){
            throw new BusinessException("抢购失败，库存不足");
        }
        seckillUserManager.userSeckill(seckillGoodsEntity,userId);
    }
}
