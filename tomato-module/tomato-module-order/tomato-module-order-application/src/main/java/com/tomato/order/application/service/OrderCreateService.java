package com.tomato.order.application.service;

import com.tomato.common.exception.BusinessException;
import com.tomato.order.application.component.OrderCreateComponent;
import com.tomato.order.application.event.OrderCreateEvent;
import com.tomato.order.application.req.OrderCreateReq;
import com.tomato.order.application.resp.OrderScanCreateResp;
import com.tomato.order.domain.constants.OrderStatusEnum;
import com.tomato.order.domain.domain.entity.ChannelEntity;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.domain.repository.ChannelRepository;
import com.tomato.web.core.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * 订单新建
 *
 * @author lizhifu
 * @since  2022/12/1
 */
@Service
@Slf4j
public class OrderCreateService {
    private final OrderCreateComponent orderCreateComponent;
    private final ChannelRepository channelRepository;
    private final ApplicationContext applicationContext;
    private final Executor orderAsyncExecutor;
    public OrderCreateService(OrderCreateComponent orderCreateComponent,
                              ChannelRepository channelRepository,
                              ApplicationContext applicationContext,
                              Executor orderAsyncExecutor) {
        this.orderCreateComponent = orderCreateComponent;
        this.channelRepository = channelRepository;
        this.applicationContext = applicationContext;
        this.orderAsyncExecutor = orderAsyncExecutor;
    }

    /**
     * 扫码收单
     * @param orderCreateReq 扫码收单
     */
    public OrderScanCreateResp createScanOrder(OrderCreateReq orderCreateReq,String clientIp) {
        // TODO 是否需要异步，如果需要异步，需要考虑事务
        // TODO 是否会导致出现多余请求，是否可以将hmac校验提前
        CompletableFuture<OrderInfoEntity> orderInfoFuture = CompletableFuture.supplyAsync(
                () -> orderCreateComponent.createOrder(orderCreateReq, clientIp, OrderStatusEnum.DEAL),
                orderAsyncExecutor);

        CompletableFuture<ChannelEntity> channelEntityFuture = CompletableFuture.supplyAsync(
                () -> channelRepository.tradeChannel(orderCreateReq.getPayType(), orderCreateReq.getMerchantNo()),
                orderAsyncExecutor);
        // 堵塞等待获取结果
        OrderInfoEntity orderInfoEntity;
        try {
            orderInfoEntity = orderInfoFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("创建订单失败 error:{}",e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        ChannelEntity channelEntity;
        try {
            channelEntity = channelEntityFuture.get();
        } catch  (InterruptedException | ExecutionException e) {
            log.error("创建订单失败|发送下游渠道失败 error:{}",e.getMessage());
            throw new BusinessException(e.getMessage());
        }

        OrderScanCreateResp orderScanCreateResp = new OrderScanCreateResp();
        BeanUtil.copyProperties(orderCreateReq,orderScanCreateResp);
        orderScanCreateResp.setOrderNo(orderInfoEntity.getOrderNo());
        orderScanCreateResp.setScanUrl(channelEntity.getScanUrl());

        // 发布事件 TODO 后续如果有事务，需要迁出
        OrderCreateEvent orderCreateEvent = new OrderCreateEvent(this,orderInfoEntity);
        applicationContext.publishEvent(orderCreateEvent);
        return orderScanCreateResp;
    }
}
