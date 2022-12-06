package com.tomato.order;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExpand
 *
 * @author lizhifu
 * @date 2022/12/6
 */
public class ThreadPoolExpandTest {
    // 定义线程池
    public static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            2,
            4,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5),
            new ThreadPoolExecutor.DiscardOldestPolicy()
    ){
        @Override
        /**
         * @param t 执行任务的线程
         * @param r 将要被执行的任务
         */
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("beforeExecute执行");
        }

        /**
         * @param r 将要被执行的任务
         * @param t 异常信息
         */
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println("afterExecute执行");
        }
    };
    public static void main(String[] args) {
        poolExecutor.execute(()->{
            System.out.println("任务执行");
        });
    }
}
