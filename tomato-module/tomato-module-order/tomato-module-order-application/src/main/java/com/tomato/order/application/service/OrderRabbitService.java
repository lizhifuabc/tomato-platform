//package com.tomato.order.application.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tomato.order.domain.constant.RabbitMqConstant;
//import com.tomato.order.order.domain.bo.OrderDelayBO;
//import com.tomato.rabbitmq.handler.RabbitTemplateConfirmReturn;
//import com.tomato.rabbitmq.handler.RabbitTemplateReturnsCallback;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.ReturnedMessage;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Service;
//
//
///**
// * 订单mq服务
// *
// * @author lizhifu
// * @since 2022/12/20
// */
//@Service
//@Slf4j
//public class OrderRabbitService {
//    private final RabbitTemplate rabbitTemplate;
//    private final ObjectMapper objectMapper;
//    public OrderRabbitService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.objectMapper = objectMapper;
//    }
//    public void delayOrder(OrderDelayBO orderDelayBO) {
//        log.info("准备发送延迟订单到MQ：{}", orderDelayBO);
//        rabbitTemplate.setConfirmCallback(new RabbitTemplateConfirmReturn() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                super.confirm(correlationData, ack, cause);
//            }
//        });
//
//        rabbitTemplate.setReturnsCallback(new RabbitTemplateReturnsCallback() {
//            @Override
//            public void returnedMessage(ReturnedMessage returned) {
//                super.returnedMessage(returned);
//            }
//        });
//        // 数据发送到 exchange
//        CorrelationData correlationData = new CorrelationData();
//        rabbitTemplate.convertAndSend(
//                RabbitMqConstant.ORDER_DELAY_EXCHANGE,
//                RabbitMqConstant.ORDER_DELAY_ROUTING_KEY,
//                orderDelayBO,
//                msg -> {
//                    // 设置消息属性 延迟（毫秒）
//                    msg.getMessageProperties().setDelay(5000);
//                    return msg;
//                },
//                correlationData);
//    }
//}
