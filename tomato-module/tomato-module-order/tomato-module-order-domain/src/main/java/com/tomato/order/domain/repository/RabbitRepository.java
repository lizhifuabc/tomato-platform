package com.tomato.order.domain.repository;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;

/**
 * Repository只负责Entity对象的存储和读取，而Repository的实现类完成数据库存储的细节。
 *
 * @author lizhifu
 * @since 2023/8/2
 */
public interface RabbitRepository {
    /**
     * 创建订单超时
     * @param orderInfoEntity 订单信息
     */
    void createOrderTimeOut(OrderInfoEntity orderInfoEntity);

    /**
     * 订单完成
     * @param orderInfoEntity 订单信息
     */
    void orderComplete(OrderInfoEntity orderInfoEntity);
}
