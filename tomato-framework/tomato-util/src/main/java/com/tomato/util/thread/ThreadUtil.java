package com.tomato.util.thread;

/**
 * 线程工具
 *
 * @author lizhifu
 * @since 2022/12/26
 */
public class ThreadUtil {
    /**
     * 挂起当前线程
     *
     * @param millis 挂起的毫秒数
     * @return 被中断返回false，否则true
     */
    public static boolean sleep(long millis) {
        if (millis > 0) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }
}
