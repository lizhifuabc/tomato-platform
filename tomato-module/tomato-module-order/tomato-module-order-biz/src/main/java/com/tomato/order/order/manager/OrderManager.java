package com.tomato.order.order.manager;

import com.tomato.order.domain.entity.OrderEntity;
import com.tomato.order.order.dao.OrderDao;
import com.tomato.order.order.domain.bo.UpdateOrderStatusBO;
import com.tomato.order.domain.constant.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * 订单 manager
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@Service
@Slf4j
public class OrderManager {
    private final OrderDao orderDao;
    private final OrderNoManager orderNoManager;
    public OrderManager(OrderDao orderDao,OrderNoManager orderNoManager) {
        this.orderDao = orderDao;
        this.orderNoManager = orderNoManager;
    }

    /**
     * 完成订单：只能从 DEAL-->终态
     * @param orderNo 订单号
     * @param orderStatus 终态：支付成功、支付失败、支付撤销、订单关闭
     * @param currentVersion 当前版本号
     * @return 更新条数
     */
    public int completeOrder(String orderNo,String orderStatus,Integer currentVersion){
        UpdateOrderStatusBO updateOrderStatusBO = UpdateOrderStatusBO.builder()
                .orderNo(orderNo)
                .expectOrderStatus(OrderStatusEnum.DEAL.getValue())
                .completeTime(LocalDateTime.now())
                .orderStatus(orderStatus)
                .currentVersion(currentVersion)
                .build();
        return orderDao.updateOrderStatus(updateOrderStatusBO);
    }
    /**
     * 设置订单为支付中：只能从 INIT-->DEAL
     * @param orderNo 订单号
     * @param currentVersion 当前版本号
     * @return 更新条数
     */
    public int dealOrder(String orderNo,Integer currentVersion){
        UpdateOrderStatusBO updateOrderStatusBO = UpdateOrderStatusBO.builder()
                .orderNo(orderNo)
                .expectOrderStatus(OrderStatusEnum.INIT.getValue())
                .orderStatus(OrderStatusEnum.DEAL.getValue())
                .currentVersion(currentVersion)
                .build();
        return orderDao.updateOrderStatus(updateOrderStatusBO);
    }

    public void createOrderDefault(OrderEntity orderEntity){
        orderEntity.setOrderStatus(OrderStatusEnum.DEAL.getValue());
        // 5 分钟后超时
        orderEntity.setTimeoutTime(LocalDateTime.now().plusMinutes(5));
        orderEntity.setOrderNo(orderNoManager.genOrderNo(orderEntity.getMerchantOrderNo(),orderEntity.getMerchantNo()));
        orderEntity.setMachineIp(ip());
        orderDao.insert(orderEntity);
    }

    private String ip(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("OrderManager 获取本机IP UnknownHostException",e);
            return "127.0.0.1";
        }
    }
}
