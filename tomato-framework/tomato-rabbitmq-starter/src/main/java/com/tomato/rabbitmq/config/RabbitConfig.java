package com.tomato.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * rabbitmq配置
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@AutoConfiguration
public class RabbitConfig {
    /**
     * 消息转换器修改
     * 有了这项配置后, 消息在rabbitMq的管理页面就可以显示看到消息的json数据，否则是序列化的数据
     *
     * @return
     */
    @Bean
    public MessageConverter jackJsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate 配置
     *
     * @param connectionFactory connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,MessageConverter jackJsonMessageConverter){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        // 开启强制消息投递
        // 消息未被路由至任何一个queue，则回退一条消息到RabbitTemplate.ReturnCallback中的returnedMessage方法
        // TODO 原因：消息写磁盘和从磁盘读取消息发送存在时间差，两个时间点的队列和连接情况可能不同。所以不支持Mandatory设置
        // template.setMandatory(true);
        template.setMessageConverter(jackJsonMessageConverter);
        return template;
    }
}
