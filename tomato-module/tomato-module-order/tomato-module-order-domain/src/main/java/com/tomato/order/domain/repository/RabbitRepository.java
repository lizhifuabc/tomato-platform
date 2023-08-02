package com.tomato.order.domain.repository;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;

/**
 * Repository只负责Entity对象的存储和读取，而Repository的实现类完成数据库存储的细节。
 *
 * @author lizhifu
 * @since 2023/8/2
 */
public interface RabbitRepository {
    void createOrderTimeOut(OrderInfoEntity orderInfoEntity);
}
