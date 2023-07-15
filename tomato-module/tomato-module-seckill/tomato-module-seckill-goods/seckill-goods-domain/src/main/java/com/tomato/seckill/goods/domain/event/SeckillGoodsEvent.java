package com.tomato.seckill.goods.domain.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * 秒杀活动商品事件
 *
 * @author lizhifu
 * @since 2023/7/14
 */
@Slf4j
public class SeckillGoodsEvent extends ApplicationEvent {
    private Long activityId;
    private Long id;
    private Integer status;
    public SeckillGoodsEvent(Object source) {
        super(source);
    }

    public SeckillGoodsEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
