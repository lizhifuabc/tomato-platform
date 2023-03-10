package com.tomato.book.java;

/**
 * 通过 volatile boolean 类型,实现线程间的通信。
 *
 * @author lizhifu
 * @since 2023/3/7
 */
public class VolatileMain2 {
    volatile boolean flag = false;

    public void toFlag(){
        flag = true;
    }
}
