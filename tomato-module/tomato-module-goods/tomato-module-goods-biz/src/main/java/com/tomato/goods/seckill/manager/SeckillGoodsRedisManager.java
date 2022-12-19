package com.tomato.goods.seckill.manager;

import com.tomato.goods.domain.entity.SeckillGoodsEntity;
import com.tomato.goods.seckill.dao.SeckillGoodsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Service
@Slf4j
public class SeckillGoodsRedisManager {
    private final String REDIS_QUEUE_KEY = "SECKILL:QUEUE:";
    private final SeckillGoodsDao seckillGoodsDao;
    private final StringRedisTemplate stringRedisTemplate;
    public SeckillGoodsRedisManager(SeckillGoodsDao seckillGoodsDao, StringRedisTemplate stringRedisTemplate) {
        this.seckillGoodsDao = seckillGoodsDao;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 重置所有活动数据
     * @param seckillActivityId 活动ID
     */
    public void resetSeckillActivity(Long seckillActivityId){
        List<SeckillGoodsEntity> seckillGoodsEntities = seckillGoodsDao.selectBySeckillActivityId(seckillActivityId);
        log.info("重置所有活动数据:{}",seckillGoodsEntities);
        seckillGoodsEntities.forEach(seckillGoodsEntity->{
            init(seckillGoodsEntity.getGoodsId(),seckillActivityId,seckillGoodsEntity.getSeckillRemaining());
        });
    }
    /**
     * 重置所有商品数据
     * @param goodsId 商品ID
     * @param seckillActivityId 活动ID
     */
    public Long resetSeckillGoods(Long goodsId,Long seckillActivityId){
        SeckillGoodsEntity seckillGoodsEntity = seckillGoodsDao.selectBySeckillActivityIdGoodsId(goodsId,seckillActivityId);
        return init(seckillGoodsEntity.getGoodsId(),seckillActivityId,seckillGoodsEntity.getSeckillRemaining());
    }
    /**
     * 商品库存查询
     * @param goodsId 商品ID
     * @param seckillActivityId 活动ID
     */
    public Long seckillRemaining(Long goodsId,Long seckillActivityId){
        // TODO 是否需要 数据库查询 同步 redis
        String redisKey = REDIS_QUEUE_KEY + seckillActivityId + ":" + goodsId;
        return stringRedisTemplate.opsForList().size(redisKey);
    }

    /**
     * rpop：右边出队列，获取抢到的商品
     * @param goodsId 商品id
     * @param seckillActivityId 活动ID
     * @return
     */
    public String rightPop(Long goodsId, Long seckillActivityId){
        String redisKey = REDIS_QUEUE_KEY + seckillActivityId + ":" + goodsId;
        // rpop：右边出队列，获取抢到的商品
        return stringRedisTemplate.opsForList().rightPop(redisKey);
    }

    /**
     * 左边入队列，存入秒杀活动的商品
     * @param goodsId 商品id
     * @param seckillActivityId 活动ID
     * @return 剩余库存数
     */
    public Long leftPush(Long goodsId, Long seckillActivityId){
        String redisKey = REDIS_QUEUE_KEY + seckillActivityId + ":" + goodsId;
        // lpush：左边入队列，存入秒杀活动的商品
        return stringRedisTemplate.opsForList().leftPush(redisKey,"1");
    }
    /**
     * lpush：左边入队列，存入秒杀活动的商品
     * @param goodsId 商品id
     * @param seckillActivityId 活动ID
     * @param count 数量
     * @return 剩余库存数
     */
    public Long init(Long goodsId,Long seckillActivityId,Integer count){
        String redisKey = REDIS_QUEUE_KEY + seckillActivityId + ":" + goodsId;
        log.info("SeckillGoodsRedisService init redisKey is:{}",redisKey);
        // 删除数据
        stringRedisTemplate.delete(redisKey);
        // lpush：左边入队列，存入秒杀活动的商品
        return stringRedisTemplate.opsForList().leftPushAll(redisKey, goodsList(count));
    }

    /**
     * 队列数据没有实际意义
     * @param seckillCount 数量
     * @return list
     */
    private List goodsList(int seckillCount){
        List<String> list = new LinkedList<>();
        for (int i = 0; i < seckillCount; i++) {
            list.add("1");
        }
        return list;
    }
}
