package com.tomato.order.application.service;

import com.tomato.order.application.component.OrderCreateComponent;
import com.tomato.order.application.req.OrderCreateReq;
import com.tomato.order.application.resp.OrderScanCreateResp;
import com.tomato.order.domain.constants.OrderStatusEnum;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.web.core.util.BeanUtil;
import org.springframework.stereotype.Service;

/**
 * 订单新建
 *
 * @author lizhifu
 * @since  2022/12/1
 */
@Service
public class OrderCreateService {
    private final OrderCreateComponent orderCreateComponent;

    public OrderCreateService(OrderCreateComponent orderCreateComponent) {
        this.orderCreateComponent = orderCreateComponent;
    }

    /**
     * 扫码收单
     * @param orderCreateReq 扫码收单
     */
    public OrderScanCreateResp createScanOrder(OrderCreateReq orderCreateReq,String clientIp) {
        OrderInfoEntity orderInfoEntity = orderCreateComponent.createOrder(orderCreateReq,clientIp, OrderStatusEnum.DEAL);
        OrderScanCreateResp orderScanCreateResp = new OrderScanCreateResp();
        BeanUtil.copyProperties(orderCreateReq,orderScanCreateResp);
        orderScanCreateResp.setOrderNo(orderInfoEntity.getOrderNo());
        orderScanCreateResp.setScanUrl("http://www.baidu.com");
        return orderScanCreateResp;
    }
}
