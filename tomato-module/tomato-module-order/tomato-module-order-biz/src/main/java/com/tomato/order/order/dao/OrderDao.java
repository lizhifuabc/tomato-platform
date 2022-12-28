package com.tomato.order.order.dao;

import com.tomato.order.domain.entity.OrderEntity;
import com.tomato.order.domain.resp.OrderQueryResp;
import com.tomato.order.order.domain.bo.UpdateOrderStatusBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单 dao
 * {@link OrderEntity}
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

    /**
     * 订单查询
     * @param merchantNo 商户编号
     * @param merchantOrderNo 商户订单号
     * @return OrderQueryResp
     */
    OrderQueryResp selectByMerchant(@Param("merchantNo") String merchantNo,@Param("merchantOrderNo") String merchantOrderNo);

    /**
     * 订单查询
     * @param orderNo 订单号
     * @return OrderQueryResp
     */
    OrderQueryResp selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 订单查询
     * @param orderNo 订单号
     * @return OrderEntity
     */
    OrderEntity selectByOrderNoBase(@Param("orderNo") String orderNo);

    /**
     * 超时订单状态更新
     * TODO 大数据量处理
     * @return 更新条数
     */
    int updateOrderStatusTimeOut();

    /**
     * 订单查询
     * @param pageIndex 当前页码
     * @param pageSize 查询数量
     * @param createTime 创建时间
     * @param orderStatus 订单状态
     * @return 订单
     */
    List<OrderEntity> selectByCreateTime(@Param("pageIndex") int pageIndex,
                                         @Param("pageSize") int pageSize,
                                         @Param("createTime") LocalDateTime createTime,
                                         @Param("orderStatus") String orderStatus);
}
