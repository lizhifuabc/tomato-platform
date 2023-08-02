package com.tomato.order.domain.repository;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;

/**
 * Repository只负责Entity对象的存储和读取，而Repository的实现类完成数据库存储的细节。
 * 通过加入Repository接口，底层的数据库连接可以通过不同的实现类而替换。
 *
 * @author lizhifu
 * @since 2023/8/1
 */
public interface OrderInfoRepository {
    /**
     * 订单新建
     * @param orderInfoEntity 订单新建
     */
    void createOrder(OrderInfoEntity orderInfoEntity);

    /**
     * 更新超时订单订单状态
     * @return 更新条数
     */
    int updateTimeOutOrder();
}
