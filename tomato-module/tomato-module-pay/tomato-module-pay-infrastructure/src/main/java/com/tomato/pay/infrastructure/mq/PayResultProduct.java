package com.tomato.pay.infrastructure.mq;

import com.tomato.pay.domain.constants.PayMqConstant;
import com.tomato.pay.domain.event.PayResultEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * 支付结果通知
 *
 * @author lizhifu
 * @since 2023/4/12
 */
@Slf4j
@Component
public class PayResultProduct {
    private final RabbitTemplate rabbitTemplate;

    public PayResultProduct(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void send(PayResultEvent payResultEvent) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("支付结果通知消息发送，Keys：{}，消息内容：{}", correlationData.getId(), payResultEvent);
        CompletableFuture<CorrelationData.Confirm> future = correlationData.getFuture();
        future.whenComplete((confirm, throwable) -> {
            if (confirm.isAck()) {
                log.info("支付结果通知消息发送成功，Keys：{}，消息内容：{}", correlationData.getId(), payResultEvent);
            } else {
                log.error("支付结果通知消息发送失败，Keys：{}，消息内容：{}", correlationData.getId(), payResultEvent);
            }
        });
        // rabbitTemplate.convertSendAndReceive(PayMqConstant.PAY_RESULT_EXCHANGE, PayMqConstant.PAY_RESULT_ROUTING_KEY, payResultEvent);
        rabbitTemplate.convertAndSend(PayMqConstant.PAY_RESULT_EXCHANGE, "", payResultEvent,correlationData);
    }
}
