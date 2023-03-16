package com.tomato.seckill.manager;

import com.tomato.domain.exception.BusinessException;
import com.tomato.seckill.dao.SeckillGoodsDao;
import com.tomato.seckill.domain.entity.SeckillGoodsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
            init(seckillGoodsEntity.getId(),seckillActivityId,seckillGoodsEntity.getSeckillRemaining());
        });
    }
    /**
     * 重置所有商品数据
     * @param seckillGoodsId 秒杀活动商品ID
     */
    public Long resetSeckillGoods(Long seckillGoodsId){
        SeckillGoodsEntity seckillGoodsEntity = seckillGoodsDao.selectById(seckillGoodsId);
        return init(seckillGoodsEntity.getId(),seckillGoodsEntity.getSeckillActivityId(),seckillGoodsEntity.getSeckillRemaining());
    }
    /**
     * 商品库存查询
     * @param seckillGoodsId  秒杀活动商品ID {@link SeckillGoodsEntity }
     * @param seckillActivityId 活动ID
     */
    public Long seckillRemaining(Long seckillGoodsId,Long seckillActivityId){
        // TODO 是否需要 数据库查询 同步 redis
        String redisKey = REDIS_QUEUE_KEY + seckillActivityId + ":" + seckillGoodsId;
        return stringRedisTemplate.opsForList().size(redisKey);
    }

    /**
     * 扣减库存
     * rpop：右边出队列，获取抢到的商品
     * @param seckillGoodsId 秒杀活动商品ID
     * @param seckillActivityId 活动ID
     * @return
     */
    public String deductSeckillGoods(Long seckillGoodsId, Long seckillActivityId){
        String redisKey = REDIS_QUEUE_KEY + seckillActivityId + ":" + seckillGoodsId;
        // rpop：右边出队列，获取抢到的商品
        String rightPop = stringRedisTemplate.opsForList().rightPop(redisKey);
        if(Objects.isNull(rightPop)){
            throw new BusinessException("抢购失败，库存不足");
        }
        return rightPop;
    }

    /**
     * 左边入队列，存入秒杀活动的商品
     * @param seckillGoodsId 秒杀活动商品ID
     * @param seckillActivityId 活动ID
     * @return 剩余库存数
     */
    public Long leftPush(Long seckillGoodsId, Long seckillActivityId){
        String redisKey = REDIS_QUEUE_KEY + seckillActivityId + ":" + seckillGoodsId;
        // lpush：左边入队列，存入秒杀活动的商品
        return stringRedisTemplate.opsForList().leftPush(redisKey,"1");
    }
    /**
     * lpush：左边入队列，存入秒杀活动的商品
     * @param seckillGoodsId 秒杀活动商品ID
     * @param seckillActivityId 活动ID
     * @param count 数量
     * @return 剩余库存数
     */
    public Long init(Long seckillGoodsId,Long seckillActivityId,Integer count){
        String redisKey = REDIS_QUEUE_KEY + seckillActivityId + ":" + seckillGoodsId;
        log.info("SeckillGoodsRedisService init redisKey is:{}",redisKey);
        // 删除数据
        stringRedisTemplate.delete(redisKey);
        if (count == 0){
            return 0L;
        }
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
