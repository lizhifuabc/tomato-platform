package com.tomato.order.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * demo
 *
 * @author lizhifu
 * @date 2022/12/6
 */
@Service
@Slf4j
public class AsyncDemo {
    @Async("asyncTaskExecutor")
    public void demo(){
        log.info("thread is:{}",Thread.currentThread());
    }
}
