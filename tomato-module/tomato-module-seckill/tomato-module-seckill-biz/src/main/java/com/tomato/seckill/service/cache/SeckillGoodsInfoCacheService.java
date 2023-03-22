package com.tomato.seckill.service.cache;

import com.tomato.goods.feign.RemoteGoodsService;
import com.tomato.seckill.dao.SeckillGoodsDao;
import org.springframework.stereotype.Service;

/**
 * 秒杀活动商品缓存
 *
 * @author lizhifu
 * @since 2023/3/22
 */
@Service
public class SeckillGoodsInfoCacheService {
    private final RemoteGoodsService remoteGoodsService;
    private final SeckillGoodsDao seckillGoodsDao;
    public SeckillGoodsInfoCacheService(RemoteGoodsService remoteGoodsService, SeckillGoodsDao seckillGoodsDao) {
        this.remoteGoodsService = remoteGoodsService;
        this.seckillGoodsDao = seckillGoodsDao;
    }

    /**
     * 缓存
     * 秒杀的数据量不大，可以直接缓存到redis中，目前设置为 15 个商品
     * TODO 优化：秒杀的数据量大的时候，mongoDB,es 等等
     * @param seckillActivityId 秒杀活动id
     */
    public void cache(Long seckillActivityId){
        seckillGoodsDao.selectById(seckillActivityId);
//        remoteGoodsService.queryGoodsInfoList(1L);
    }
}
