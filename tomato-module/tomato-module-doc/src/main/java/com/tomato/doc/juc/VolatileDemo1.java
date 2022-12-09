package com.tomato.doc.juc;

/**
 * VolatileDemo
 *
 * @author lizhifu
 * @date 2022/12/8
 */
public class VolatileDemo1 {
    private volatile int i = 0;
    public void add(){
        i++;
    }
}
