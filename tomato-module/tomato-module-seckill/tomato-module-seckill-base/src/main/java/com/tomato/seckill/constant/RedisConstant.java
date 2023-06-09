package com.tomato.seckill.constant;

/**
 * redis key 常量
 *
 * @author lizhifu
 * @since 2023/3/17
 */
public class RedisConstant {
    /**
     * 秒杀活动商品基本信息key
     */
    public static final String SECKILL_GOODS_INFO = "SECKILL:GOODS:INFO:";
    /**
     * 秒杀活动商品库存信息key
     */
    public static final String SECKILL_GOODS = "SECKILL:GOODS:";

    /**
     * 秒杀活动商品库存信息key
     */
    public static final String SECKILL_GOODS_SECKILL = "SECKILL:GOODS:SECKILL:";

    /**
     * 商品购买用户key
     */
    public static final String SECKILL_GOODS_SECKILL_USER = "SECKILL:GOODS:SECKILL:USER:";
}
