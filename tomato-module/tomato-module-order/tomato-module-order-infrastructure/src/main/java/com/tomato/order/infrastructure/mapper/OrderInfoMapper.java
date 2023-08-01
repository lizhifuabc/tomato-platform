package com.tomato.order.infrastructure.mapper;

import com.tomato.mybatis.mapper.BaseMapper;
import com.tomato.order.infrastructure.mapper.dataobject.OrderInfoDO;
import com.tomato.order.infrastructure.mapper.dataobject.UpdateOrderStatusDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单信息
 *
 * @author lizhifu
 * @since 2023/4/7
 */
@Repository
public interface OrderInfoMapper extends BaseMapper<OrderInfoDO,Long> {
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

    /**
     * 更新订单状态
     * @param updateOrderStatusDO 更新订单状态
     * @return 更新条数
     */
    int updateOrderStatus(@Param("updateOrderStatusBO") UpdateOrderStatusDO updateOrderStatusDO);

    /**
     * 订单查询
     * @param orderNo 订单号
     * @return OrderEntity
     */
    OrderInfoDO selectByOrderNoBase(@Param("orderNo") String orderNo);

    /**
     * 超时订单状态更新
     * TODO 大数据量处理
     * TODO 分库分表
     * @return 更新条数
     */
    int updateOrderStatusTimeOut();

    /**
     * 订单查询
     * TODO 分库分表
     * @param pageIndex 当前页码
     * @param pageSize 查询数量
     * @param createTime 创建时间
     * @param orderStatus 订单状态
     * @return 订单
     */
    List<OrderInfoDO> selectByCreateTime(@Param("pageIndex") int pageIndex,
                                         @Param("pageSize") int pageSize,
                                         @Param("createTime") LocalDateTime createTime,
                                         @Param("orderStatus") String orderStatus);
}
