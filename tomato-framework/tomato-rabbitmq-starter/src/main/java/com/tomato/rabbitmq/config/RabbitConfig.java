package com.tomato.rabbitmq.config;

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
}
