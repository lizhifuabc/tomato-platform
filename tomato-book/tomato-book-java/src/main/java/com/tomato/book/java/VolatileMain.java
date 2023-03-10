package com.tomato.book.java;

/**
 * Volatile 修饰的变量，每次都会从主内存中读取，而不是从线程的工作内存中读取
 *
 * @author lizhifu
 * @since 2023/3/7
 */
public class VolatileMain {
    volatile int i = 0;

    /**
     * 任何线程调用add()方法之后对
     * i 进行i++ 操作之后都是对其他线程可见的。
     * 操作流程：
     * 1. 从主存读取值
     * 2. 工作内存中+1操作
     * 3. 写回主存
     */
    public void add(){
        i++;
    }
}
