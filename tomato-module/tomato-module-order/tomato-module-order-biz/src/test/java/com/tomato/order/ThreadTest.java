package com.tomato.order;

/**
 * ThreadTest
 *
 * @author lizhifu
 * @date 2022/12/3
 */
public class ThreadTest {
    public static void main(String[] args) {
        int num = Runtime.getRuntime().availableProcessors();
        System.out.println(num);
    }
}
