package com.tomato.seckill.manager;

import com.tomato.domain.resp.MultiResp;
import com.tomato.goods.domain.resp.GoodsInfoResp;
import com.tomato.goods.feign.RemoteGoodsService;
import com.tomato.seckill.constant.RedisConstant;
import com.tomato.seckill.dao.SeckillGoodsDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 秒杀活动商品缓存
 *
 * @author lizhifu
 * @since 2023/3/22
 */
@Service
@Slf4j
public class SeckillGoodsInfoCacheManager {

    private final RemoteGoodsService remoteGoodsService;
    private final SeckillGoodsDao seckillGoodsDao;
    private final StringRedisTemplate stringRedisTemplate;

    public SeckillGoodsInfoCacheManager(RemoteGoodsService remoteGoodsService, SeckillGoodsDao seckillGoodsDao, StringRedisTemplate stringRedisTemplate) {
        this.remoteGoodsService = remoteGoodsService;
        this.seckillGoodsDao = seckillGoodsDao;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    /**
     * 缓存
     * 秒杀的数据量不大，可以直接缓存到redis中
     * TODO 优化：秒杀的数据量大的时候，mongoDB,es 等等
     * @param seckillActivityId 秒杀活动id
     */
    public void cache(Long seckillActivityId){
        // 删除缓存 TODO bigkey 优化
        stringRedisTemplate.opsForList().getOperations().delete(RedisConstant.SECKILL_GOODS_INFO + seckillActivityId);
        // 缓存秒杀商品信息
        String key = RedisConstant.SECKILL_GOODS_INFO + seckillActivityId;
        List<Long> idList = seckillGoodsDao.selectIdBySeckillActivityId(seckillActivityId);
        MultiResp<GoodsInfoResp> goodsInfoList = remoteGoodsService.queryGoodsInfoList(idList);
        if (Objects.nonNull(goodsInfoList) && goodsInfoList.isSuccess()){
            List<String> goodsInfoListStr = goodsInfoList.getData().stream().map(this::beanToString).collect(Collectors.toList());
            ListOperations<String, String> listOps = stringRedisTemplate.opsForList();
            listOps.rightPushAll(key, goodsInfoListStr);
            log.info("缓存秒杀商品信息成功,seckillActivityId:{},key:{}",seckillActivityId,key);
            return;
        }
        log.error("缓存秒杀商品信息失败,商品信息获取失败,seckillActivityId:{},key:{}",seckillActivityId,key);
    }

    /**
     * bean转string
     * @param goodsInfoResp 商品信息
     * @return string
     */
    private String beanToString(GoodsInfoResp goodsInfoResp){
        try {
            return BeanUtils.describe(goodsInfoResp).toString();
        } catch (Exception ignored) {
            log.error("缓存秒杀商品信息失败,goodsInfoId{},beanToString error",goodsInfoResp.getId(),ignored);
            return "";
        }
    }
}
