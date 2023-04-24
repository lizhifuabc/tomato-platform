package com.tomato.notice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.notice.constant.RabbitMqConstant;
import com.tomato.notice.domain.bo.NoticeDelayBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


/**
 * 通知mq服务
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@Service
@Slf4j
public class NoticeRabbitService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    public NoticeRabbitService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 通知
     *
     * @param noticeDelayBO 通知延迟
     */
    public void delayNotice(NoticeDelayBO noticeDelayBO) {
        log.info("准备发送延迟订单到MQ：{}", noticeDelayBO);
        // 数据发送到 exchange
        CorrelationData correlationData = new CorrelationData();
        rabbitTemplate.convertAndSend(
                RabbitMqConstant.NOTICE_DELAY_EXCHANGE,
                RabbitMqConstant.NOTICE_DELAY_ROUTING_KEY,
                noticeDelayBO,
                msg -> {
                    // 设置消息属性 延迟（毫秒）
                    // 通知延时次数
                    //        1   2  3  4   5   6
                    //        0  30 60 90 120 150
                    msg.getMessageProperties().setDelay((noticeDelayBO.getNoticeCount()) * (1000 * 30));
                    return msg;
                },
                correlationData);
    }
}
