package com.tomato.order;

import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池状态测试
 *
 * @author lizhifu
 * @date 2022/12/7
 */
public class ThreadPoolStateTest {
    /**
     * 自定义线程池
     */
    public static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            2,
            4,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000)
    );

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            poolExecutor.execute(() -> {
                System.out.print(1);
                ThreadUtilTest.sleep(1000);
            });
        }


        while (true) {
            System.out.println();

            int queueSize = poolExecutor.getQueue().size();
            System.out.println("当前排队线程数：" + queueSize);

            int activeCount = poolExecutor.getActiveCount();
            System.out.println("当前活动线程数：" + activeCount);

            long completedTaskCount = poolExecutor.getCompletedTaskCount();
            System.out.println("执行完成线程数：" + completedTaskCount);

            long taskCount = poolExecutor.getTaskCount();
            System.out.println("总任务数量：" + taskCount);

            ThreadUtilTest.sleep(3000);
            poolExecutor.shutdownNow();
        }
    }
}
