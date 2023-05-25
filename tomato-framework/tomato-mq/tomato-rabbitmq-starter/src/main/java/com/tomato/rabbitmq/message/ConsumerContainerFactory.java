package com.tomato.rabbitmq.message;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Objects;

/**
 * MQ具体消息监听器工厂
 *
 * @author lizhifu
 * @since 2023/5/23
 */
@Data
@Slf4j
@Builder
public class ConsumerContainerFactory implements FactoryBean<SimpleMessageListenerContainer> {
    /**
     * MQ连接工厂
     */
    private ConnectionFactory connectionFactory;

    /**
     * 操作MQ管理器
     */
    private AmqpAdmin amqpAdmin;

    /**
     * 队列
     */
    private Queue queue;

    /**
     * 交换机
     */
    private Exchange exchange;

    /**
     * 消费者
     */
    private ConsumerService consumer;

    /**
     * 重试回调
     */
    private CustomRetryListener retryListener;

    /**
     * 最大重试次数
     */
    private final Integer maxAttempts = 5;

    /**
     * 是否自动确认
     */
    private Boolean autoAck;
    /**
     * 并发消费者数量
     */
    private int concurrentConsumers;
    /**
     * 最大并发消费者数量
     */
    private int maxConcurrentConsumers;
    /**
     * 消息预取（prefetch）：可以配置每个消费者从 RabbitMQ 中预先获取的消息数量，以控制消息的批量处理。
     * 限制每次只消费一个(一个线程)，上面配置5，也就是能一次接收5个。
     */
    private int prefetchCount;
    /**
     * 消息消费的监听容器
     * @return SimpleMessageListenerContainer
     */
    @Override
    public SimpleMessageListenerContainer getObject() throws Exception {
        // 使用共享的消息队列,消费者线程从队列中获取消息并消费,可以避免消息重复消费但消费速度较慢。
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setAmqpAdmin(amqpAdmin);
        // 设置 RabbitMQ 的连接工厂，用于创建 RabbitMQ 连接
        container.setConnectionFactory(connectionFactory);
        // 设置监听的队列，可以设置一个或多个队列。
        container.setQueues(queue);
        // 消息预取（prefetch）：可以配置每个消费者从 RabbitMQ 中预先获取的消息数量，以控制消息的批量处理。
        container.setPrefetchCount(prefetchCount);
        // 并发消费：可以配置容器中的消费者线程数，以实现消息的并发消费。
        container.setConcurrentConsumers(concurrentConsumers);
        // 设置最大并发消费者的数量，即最大的消息并发处理线程数。
        container.setMaxConcurrentConsumers(maxConcurrentConsumers);
        // 是否将被拒绝的消息重新入队列,设置为 false 时，表示如果消息被消费者拒绝，那么该消息将被视为已成功处理，不会重新放回队列
        // 仅在确认模式为 MANUAL 时生效。在 AUTO 确认模式下，被拒绝的消息会自动重新入队列。
        container.setDefaultRequeueRejected(Boolean.FALSE);
        container.setAdviceChain(createRetry());
        // 设置消息确认模式:NONE：不进行消息确认。AUTO：自动确认消息。MANUAL：手动确认消息(消费者需要显式调用 channel.basicAck() 方法来确认消息的消费。)。
        container.setAcknowledgeMode(autoAck ? AcknowledgeMode.AUTO : AcknowledgeMode.MANUAL);
        if (Objects.nonNull(consumer)) {
            container.setMessageListener(consumer);
        }
        return container;
    }
    /**
     * 配置重试
     * @return
     */
    private Advice createRetry() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.registerListener(new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
                // 第一次重试调用
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {

            }

            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                if (Objects.nonNull(retryListener)) {
                    retryListener.onRetry(context, callback, throwable);
                    if (maxAttempts.equals(context.getRetryCount())) {
                        retryListener.lastRetry(context, callback, throwable);
                    }
                }
            }
        });
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(maxAttempts));
        retryTemplate.setBackOffPolicy(genExponentialBackOffPolicy());
        return RetryInterceptorBuilder.stateless()
                .retryOperations(retryTemplate).recoverer(new RejectAndDontRequeueRecoverer()).build();
    }

    /**
     * 设置过期时间
     * @return
     */
    private BackOffPolicy genExponentialBackOffPolicy() {
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        // 重试间隔基数(秒)
        backOffPolicy.setInitialInterval(5000);
        // 从重试的第一次至最后一次，最大时间间隔(秒)
        backOffPolicy.setMaxInterval(86400000);
        // 重试指数
        backOffPolicy.setMultiplier(1);
        return backOffPolicy;
    }
    @Override
    public Class<?> getObjectType() {
        return SimpleMessageListenerContainer.class;
    }
}
