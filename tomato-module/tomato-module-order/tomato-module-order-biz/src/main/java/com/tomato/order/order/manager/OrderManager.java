package com.tomato.order.order.manager;

import com.tomato.domain.type.CommonStatusEnum;
import com.tomato.order.order.dao.OrderDao;
import com.tomato.order.order.domain.bo.UpdateOrderStatusBO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 订单 manager
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@Service
public class OrderManager {
    private final OrderDao orderDao;

    public OrderManager(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * 完成订单
     * @param orderNo 订单号
     * @param orderStatus 状态：成功、失败、取消等终态
     * @param currentVersion 当前版本号
     * @return
     */
    public int completeOrder(String orderNo,String orderStatus,Integer currentVersion){
        UpdateOrderStatusBO updateOrderStatusBO = UpdateOrderStatusBO.builder()
                .orderNo(orderNo)
                .expectOrderStatus(CommonStatusEnum.DEAL.getValue())
                .completeTime(LocalDateTime.now())
                .orderStatus(orderStatus)
                .currentVersion(currentVersion)
                .build();
        return orderDao.updateOrderStatus(updateOrderStatusBO);
    }
}
