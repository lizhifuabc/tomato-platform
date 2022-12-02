package com.tomato.order.order.dao;

import com.tomato.order.order.domain.bo.UpdateOrderStatusBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单 dao
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@Mapper
public interface OrderDao {
    /**
     * 更新订单状态
     * @param updateOrderStatusBO 更新订单状态
     * @return 更新条数
     */
    int updateOrderStatus(@Param("updateOrderStatusBO") UpdateOrderStatusBO updateOrderStatusBO);
}
