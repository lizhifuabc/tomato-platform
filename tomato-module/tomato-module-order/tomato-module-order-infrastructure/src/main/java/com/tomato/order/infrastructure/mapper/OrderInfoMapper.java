package com.tomato.order.infrastructure.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 订单信息
 *
 * @author lizhifu
 * @since 2023/4/7
 */
@Repository
public interface OrderInfoMapper {
    /**
     * 订单查询
     * @param merchantNo 商户编号
     * @param merchantOrderNo 商户订单号
     * @return 订单信息
     */
    OrderInfoDO selectByMerchant(@Param("merchantNo") String merchantNo, @Param("merchantOrderNo") String merchantOrderNo);

    /**
     * 订单查询
     * @param orderNo 订单号
     * @return 订单信息
     */
    OrderInfoDO selectByOrderNo(@Param("orderNo") String orderNo);
}
