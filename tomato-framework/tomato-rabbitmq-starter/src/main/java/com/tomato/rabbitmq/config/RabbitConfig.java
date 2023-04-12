package com.tomato.rabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.jackson.config.JacksonConfiguration;
import com.tomato.rabbitmq.monitor.RabbitTemplateConfig;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;

/**
 * rabbitmq配置
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@AutoConfiguration
public class RabbitConfig{
    private static final Logger log = LoggerFactory.getLogger(JacksonConfiguration.class);
    @PostConstruct
    public void init(){
        log.info("tomato-rabbitmq-starter Auto Configure.");
    }
    /**
     * 用于将消息序列化为 JSON 格式
     * 消息在rabbitMq的管理页面就可以显示看到消息的json数据，否则是序列化的数据
     *
     * @return MessageConverter
     */
    @Bean
    public MessageConverter jackJsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /**
     * RabbitTemplate 配置 发送和接收 RabbitMQ 消息
     *
     * @param connectionFactory connectionFactory
     * @return RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,MessageConverter jackJsonMessageConverter){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        // 开启强制消息投递
        // 消息无法路由到指定的队列时，将消息退回给生产者或发送到备用交换机（如果备用交换机已经配置）。
        // 如果开启了mandatory，无法路由的消息会返回给生产者，生产者可以通过实现ReturnCallback接口的returnedMessage方法进行处理，
        // 如果没有实现ReturnCallback接口，那么无法路由的消息会被直接丢弃。
        // template.setMandatory(true);
        template.setMessageConverter(jackJsonMessageConverter);

        return template;
    }

    @Bean
    public RabbitTemplateConfig rabbitThreadPoolMonitor(RabbitTemplate rabbitTemplate, RabbitProperties rabbitProperties){
        return new RabbitTemplateConfig(rabbitTemplate, rabbitProperties);
    }
}
