package com.tomato.notice.mq;

import com.rabbitmq.client.Channel;
import com.tomato.domain.core.exception.BusinessException;
import com.tomato.notice.constant.RabbitMqConstant;
import com.tomato.notice.domain.bo.NoticeDelayBO;
import com.tomato.notice.service.NoticeSendService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 商户通知mq
 *
 * @author lizhifu
 * @since 2023/1/4
 */
@Slf4j
@Component
public class NoticeMQReceiver {
    @Resource
    private NoticeSendService noticeSendService;
    @RabbitListener(queues = RabbitMqConstant.NOTICE_DELAY_QUEUE,ackMode = "MANUAL",concurrency = "2-4")
    public void delay(@Payload NoticeDelayBO noticeDelayBO, Message message, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        log.info("商户通知mq监听器监听到延迟队列：订单 {}",noticeDelayBO);
        try {
            noticeSendService.send(noticeDelayBO.getId());
            // 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息（成功消费，消息从队列中删除 ）
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (BusinessException e) {
            log.info("商户通知mq延迟队列：订单 {} 失败 {}",noticeDelayBO,e.getMessage());
            // ack返回false，requeue-true并重新回到队列
            // channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            // 第二个参数，true会重新放回队列，根据业务逻辑判断什么时候使用拒绝
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
