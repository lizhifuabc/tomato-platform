package com.tomato.order.demo;

import lombok.SneakyThrows;
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
    @SneakyThrows
    @Async("asyncTaskExecutor")
    public void demo(){
        Thread.sleep(5000L);
        log.info("thread is:{}",Thread.currentThread());
    }
}
