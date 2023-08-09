package com.tomato.order.infrastructure.mq.consume;

import com.rabbitmq.client.Channel;
import com.tomato.common.exception.BusinessException;
import com.tomato.order.domain.constants.OrderStatusEnum;
import com.tomato.order.domain.constants.RabbitMqConstant;
import com.tomato.order.infrastructure.dataobject.OrderDelayDO;
import com.tomato.order.infrastructure.dataobject.OrderInfoDO;
import com.tomato.order.infrastructure.dataobject.UpdateOrderStatusDO;
import com.tomato.order.infrastructure.mapper.OrderInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 延迟队列监听器
 * TODO 多线程消费
 * TODO 是否需要去下游渠道做一次订单查询
 * TODO 订单超时了，此时回调处理
 * @author lizhifu
 * @since 2022/12/26
 */
@Slf4j
@Component
public class OrderTimeOutDelayListener {
    private final OrderInfoMapper orderInfoMapper;

    public OrderTimeOutDelayListener(OrderInfoMapper orderInfoMapper) {
        this.orderInfoMapper = orderInfoMapper;
    }

    /**
     * 延迟队列
     * @param orderDelayDO 订单
     * @param message 消息
     * @param channel channel 通道
     * @param headers headers 头部
     * @throws IOException IOException 异常
     */
    @RabbitListener(queues = RabbitMqConstant.ORDER_DELAY_QUEUE,ackMode = "MANUAL",concurrency = "2-4")
    public void delay(@Payload OrderDelayDO orderDelayDO, Message message, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        log.info("监听器监听到延迟队列：订单 {}",orderDelayDO);
        try {
            OrderInfoDO orderEntity = orderInfoMapper.selectByOrderNo(orderDelayDO.getOrderNo());
            // TODO 看看怎么一道 domain 层
            List<String> expectOrderStatus = Arrays.asList(OrderStatusEnum.DEAL.getValue(),OrderStatusEnum.INIT.getValue());
            UpdateOrderStatusDO updateOrderStatusDO = UpdateOrderStatusDO.builder()
                    .orderNo(orderDelayDO.getOrderNo())
                    .orderStatus(OrderStatusEnum.TIMEOUT.getValue())
                    .expectOrderStatus(expectOrderStatus)
                    .completeTime(LocalDateTime.now())
                    .currentVersion(orderEntity.getVersion())
                    .build();
            orderInfoMapper.updateOrderStatus(updateOrderStatusDO);
            // 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息（成功消费，消息从队列中删除 ）
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (BusinessException e) {
            log.info("延迟队列：订单 {} 失败 {}",orderDelayDO,e.getMessage());
            // ack返回false，requeue-true并重新回到队列
            // channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            // 第二个参数，true会重新放回队列，根据业务逻辑判断什么时候使用拒绝
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
