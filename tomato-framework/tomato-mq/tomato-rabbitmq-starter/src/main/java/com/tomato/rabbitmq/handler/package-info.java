/**
 * 实现RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback这两个接口的方法后，
 * 就可以针对性地进行消息确认的日志记录，之后做进一步的消息发送补偿，以达到接近100%投递的目的。
 *
 * @author lizhifu
 * @since 2023/5/25
 */
package com.tomato.rabbitmq.handler;