package com.tomato.order.application.service;

import com.tomato.common.exception.BusinessException;
import com.tomato.order.application.req.OrderCallbackReq;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.domain.repository.OrderInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 订单接收回调
 *
 * @author lizhifu
 * @since 2023/8/9
 */
@Service
public class OrderCallbackService {
    private final OrderInfoRepository orderInfoRepository;

    public OrderCallbackService(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    /**
     * 支付订单回调处理
     * @param orderCallbackReq 回调参数
     */
    public void callback(OrderCallbackReq orderCallbackReq) {
        // 查询订单信息
        OrderInfoEntity orderInfoEntity = orderInfoRepository.selectByOrderNo(orderCallbackReq.getOrderNo());
        Optional.ofNullable(orderInfoEntity).orElseThrow(() -> new BusinessException("订单不存在"));
        if (orderInfoEntity.finalStatus()) {
            // 订单已经完成
            return;
        }
        orderInfoEntity.success();
        orderInfoRepository.updateOrderStatus(orderInfoEntity);
    }
}
