package com.tomato.notice.service.mq.config;

import org.springframework.context.annotation.Configuration;

/**
 * 死信队列（Dead-Letter Queue）是指消息无法被正确消费或处理时，将被发送到一个预先指定的队列中。
 * 通过死信队列，我们可以对消息进行处理或重新发送，以提高系统的可靠性和稳定性。
 * <p>
 * 消息进入死信队列:
 * <p>
 * 消息被拒绝（basic.reject或basic.nack），并且requeue=false。当消费者拒绝一条消息，并且不将消息重新放回队列中（即requeue=false），则该消息将进入死信队列。
 * <p>
 * 消息过期。当消息的存活时间超过了队列的TTL（Time-To-Live，即消息过期时间）时，该消息将进入死信队列。
 * <p>
 * 队列长度限制。当队列中的消息数量超过了队列的最大长度限制时，最老的消息将被删除，并进入死信队列。
 * <p>
 * 消息被手动投递到死信队列。这通常用于将某些无法处理的消息手动发送到死信队列中，以便进行特殊的处理。
 *
 * @author lizhifu
 * @since 2023/4/16
 */
@Configuration
public class NoticeMqDeadConfig {

}
