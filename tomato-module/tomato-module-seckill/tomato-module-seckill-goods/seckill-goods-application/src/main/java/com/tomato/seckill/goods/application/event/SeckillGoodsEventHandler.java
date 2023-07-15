package com.tomato.seckill.goods.application.event;

import com.tomato.seckill.goods.domain.event.SeckillGoodsEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 商品事件处理器
 *
 * @author lizhifu
 * @since 2023/7/14
 */

@Service
@Slf4j
public class SeckillGoodsEventHandler {
    @EventListener
    // @Async
    public void onApplicationEvent(SeckillGoodsEvent event) {
        log.info("商品事件处理器:{}",event);
        // TODO

    }
}
