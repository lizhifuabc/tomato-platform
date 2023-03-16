//package com.tomato.seckill.listener;
//
//import com.rabbitmq.client.Channel;
//import com.tomato.domain.exception.BusinessException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.messaging.handler.annotation.Headers;
//
//import java.io.IOException;
//import java.util.Map;
//
///**
// * 延迟队列监听器
// * <p>NONE-自动确认</p>
// * <p>MANUAL-手动确认</p>
// * <p>AUTO-根据实际情况判断</p>
// * @author lizhifu
// * @since 2022/12/19
// */
//@Slf4j
////@Component
//public class SeckillDelayListener {
//    @RabbitListener(queues = "seckill.delay.queue",ackMode = "MANUAL")
//    public void delay(String no, Message message, Channel channel, @Headers Map<String, Object> headers) throws IOException {
//        try {
//            // 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息（成功消费，消息从队列中删除 ）
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (BusinessException e) {
//            log.info("延迟队列：秒杀 {} 失败 {}",no,e.getMessage());
//            // ack返回false，requeue-true并重新回到队列
//            // channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//            // 第二个参数，true会重新放回队列，根据业务逻辑判断什么时候使用拒绝
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//        }
//    }
//}
