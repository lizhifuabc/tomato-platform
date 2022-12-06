package com.tomato.order;

import com.tomato.order.demo.AsyncDemo;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AsyncDemo
 *
 * @author lizhifu
 * @date 2022/12/6
 */
@SpringBootTest
public class AsyncDemoTest {
    @Resource
    AsyncDemo asyncDemo;

    @SneakyThrows
    @Test
    public void test(){
        asyncDemo.demo();
        Thread.sleep(5000L);

    }
}
