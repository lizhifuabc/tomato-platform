package com.tomato.pay.infrastructure.mq.config;

import com.tomato.rabbitmq.handler.RabbitTemplateConfirmReturn;

/**
 * 消息发送成功的回调,确认消息到达MQ服务器(生产者 ==>> Exchange 的回调确认)
 *
 * @author lizhifu
 * @since 2023/4/13
 */
public class CustomRabbitTemplateConfirmReturn extends RabbitTemplateConfirmReturn {

}
