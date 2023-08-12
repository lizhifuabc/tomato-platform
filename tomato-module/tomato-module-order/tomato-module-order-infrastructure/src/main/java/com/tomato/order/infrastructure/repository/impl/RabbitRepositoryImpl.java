package com.tomato.order.infrastructure.repository.impl;

import com.tomato.order.domain.constants.RabbitMqConstant;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.domain.repository.RabbitRepository;
import com.tomato.order.infrastructure.dataobject.OrderDelayDO;
import com.tomato.rabbitmq.handler.RabbitTemplateConfirmReturn;
import com.tomato.rabbitmq.handler.RabbitTemplateReturnsCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * RabbitRepositoryImpl
 *
 * @author lizhifu
 * @since 2023/8/2
 */
@Repository
@Slf4j
public class RabbitRepositoryImpl implements RabbitRepository {
    private final RabbitTemplate rabbitTemplate;

    public RabbitRepositoryImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void createOrderTimeOut(OrderInfoEntity orderInfoEntity) {
        if (orderInfoEntity.finalStatus()) {
            log.info("订单已经完成，不需要发送延迟订单到MQ：{}", orderInfoEntity.getOrderNo());
            return;
        }
        OrderDelayDO orderDelayBO = OrderDelayDO.builder()
                .orderNo(orderInfoEntity.getOrderNo())
                .timeoutTime(orderInfoEntity.getTimeoutTime())
                .build();
        log.info("准备发送延迟订单到MQ：{}", orderDelayBO);
//        rabbitTemplate.setConfirmCallback(new RabbitTemplateConfirmReturn() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                super.confirm(correlationData, ack, cause);
//            }
//        });
//        rabbitTemplate.setReturnsCallback(new RabbitTemplateReturnsCallback() {
//            @Override
//            public void returnedMessage(ReturnedMessage returned) {
//                super.returnedMessage(returned);
//            }
//        });
        // 数据发送到 exchange
        CorrelationData correlationData = new CorrelationData();
        rabbitTemplate.convertAndSend(
                RabbitMqConstant.ORDER_DELAY_EXCHANGE,
                RabbitMqConstant.ORDER_DELAY_ROUTING_KEY,
                orderDelayBO,
                msg -> {
                    // 设置消息属性 延迟（毫秒） 过期时间 - 当前时间
                    long until = LocalDateTime.now().until(orderDelayBO.getTimeoutTime(), ChronoUnit.MILLIS);//1.相差的天数
                    msg.getMessageProperties().setDelay(Integer.parseInt(until + ""));
                    return msg;
                },
                correlationData);
    }

    @Override
    public void orderComplete(OrderInfoEntity orderInfoEntity) {
        if (!orderInfoEntity.finalStatus()) {
            log.info("订单尚未成功，不需要发送延迟订单到MQ：{}", orderInfoEntity.getOrderNo());
            return;
        }
    }
}
