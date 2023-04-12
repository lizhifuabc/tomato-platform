package com.tomato.pay.infrastructure.mq;

import com.tomato.pay.domain.constants.PayMqConstant;
import com.tomato.pay.domain.event.PayResultEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

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
        // rabbitTemplate.convertSendAndReceive(PayMqConstant.PAY_RESULT_EXCHANGE, PayMqConstant.PAY_RESULT_ROUTING_KEY, payResultEvent);
        rabbitTemplate.convertAndSend(PayMqConstant.PAY_RESULT_EXCHANGE, "", payResultEvent);
    }
}
