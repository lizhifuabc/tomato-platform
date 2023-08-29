package com.tomato.notice.service.mq;

import com.rabbitmq.client.Channel;
import com.tomato.common.exception.BusinessException;
import com.tomato.notice.common.constant.RabbitMqConstant;
import com.tomato.notice.service.service.NoticeSendService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 商户通知接收mq
 *
 * @author lizhifu
 * @since 2023/1/4
 */
@Slf4j
@Component
public class NoticeMQReceiver {

	@Resource
	private NoticeSendService noticeSendService;

	@RabbitListener(
			bindings = @QueueBinding(value = @Queue(value = RabbitMqConstant.PAY_RESULT_NOTICE_QUEUE),
					exchange = @Exchange(value = RabbitMqConstant.PAY_RESULT_EXCHANGE, type = "fanout")),
			ackMode = "MANUAL")
	public void receiver(@Payload Map<String, String> body, Message message, Channel channel,
			@Headers Map<String, Object> headers) throws IOException {
		log.info("商户通知mq收单：订单 {}", body);
		try {
			// 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息（成功消费，消息从队列中删除 ）
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}
		catch (BusinessException e) {
			log.info("商户通知mq收单：订单 {} 自定义异常 {}", body, e.getMessage());
			// ack返回false，requeue-true并重新回到队列
			// channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,
			// true);
			// 第二个参数，true会重新放回队列，根据业务逻辑判断什么时候使用拒绝
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
		}
		catch (Exception e) {
			log.error("商户通知mq收单：订单 {} 失败 {}", body, e.getMessage());
		}
	}

}
