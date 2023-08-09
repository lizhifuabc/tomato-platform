package com.tomato.order.infrastructure;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.infrastructure.dataobject.OrderInfoDO;
import com.tomato.order.infrastructure.dataobject.UpdateOrderStatusDO;
import com.tomato.web.core.util.BeanUtil;

import java.util.List;

/**
 * 订单适配器
 *
 * @author lizhifu
 * @since 2023/8/9
 */
public class OrderAdapter {
    /**
     * 转换
     * @param orderInfoDO 订单
     * @return 订单
     */
    public static OrderInfoEntity convert(OrderInfoDO orderInfoDO) {
        return BeanUtil.copy(orderInfoDO,OrderInfoEntity.class);
    }
    /**
     * 转换
     * @param list 订单
     * @return 订单
     */
    public static List<OrderInfoEntity> convert(List<OrderInfoDO> list) {
        return BeanUtil.copyList(list, OrderInfoEntity.class);
    }
    /**
     * 转换
     * @param orderInfoEntity 订单
     * @return 订单
     */
    public static OrderInfoDO convert(OrderInfoEntity orderInfoEntity) {
        return BeanUtil.copy(orderInfoEntity,OrderInfoDO.class);
    }
    /**
     * 转换
     * @param orderInfoEntity 订单
     * @return 订单
     */
    public static UpdateOrderStatusDO convertUpdateOrderStatus(OrderInfoEntity orderInfoEntity) {
        return UpdateOrderStatusDO.builder()
                .orderStatus(orderInfoEntity.getOrderStatus())
                .currentVersion(orderInfoEntity.getVersion())
                .completeTime(orderInfoEntity.getCompleteTime())
                .orderNo(orderInfoEntity.getOrderNo())
                .expectOrderStatus(orderInfoEntity.getExpectOrderStatus())
                .build();
    }
}
