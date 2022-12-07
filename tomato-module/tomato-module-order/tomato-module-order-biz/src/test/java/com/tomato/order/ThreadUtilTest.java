package com.tomato.order;

import lombok.SneakyThrows;

/**
 * ThreadUtilTest
 *
 * @author lizhifu
 * @date 2022/12/7
 */
public class ThreadUtilTest {
    @SneakyThrows
    public static void sleep(long millis){
        Thread.sleep(millis);
    }
}
