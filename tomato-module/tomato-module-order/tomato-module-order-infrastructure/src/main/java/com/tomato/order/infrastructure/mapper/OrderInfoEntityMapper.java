package com.tomato.order.infrastructure.mapper;

import com.tomato.mybatis.mapper.BaseMapper;
import com.tomato.order.infrastructure.entity.OrderInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 订单信息
 *
 * @author lizhifu
 * @since 2023/4/7
 */
@Repository
public interface OrderInfoEntityMapper extends BaseMapper<OrderInfoEntity> {
    /**
     * 订单查询
     * @param merchantNo 商户编号
     * @param merchantOrderNo 商户订单号
     * @return 订单信息
     */
    OrderInfoEntity selectByMerchant(@Param("merchantNo") String merchantNo, @Param("merchantOrderNo") String merchantOrderNo);

    /**
     * 订单查询
     * @param orderNo 订单号
     * @return 订单信息
     */
    OrderInfoEntity selectByOrderNo(@Param("orderNo") String orderNo);
}
