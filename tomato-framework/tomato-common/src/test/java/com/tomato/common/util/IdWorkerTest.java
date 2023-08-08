package com.tomato.common.util;

import java.time.LocalDateTime;

/**
 * IdWorker
 *
 * @author lizhifu
 * @since 2023/8/8
 */
public class IdWorkerTest {
    public static void main(String[] args) {
        System.out.println("当前时间：" +System.currentTimeMillis());
        IdWorker idWorker = new IdWorker(187L);
        long nextId = idWorker.nextId();
        System.out.println("生成的ID：" + nextId);

        long workerId = idWorker.getWorkerId(nextId);
        System.out.println("生成的ID转workerId："+workerId);

        long timeMillis = idWorker.getTimeMillis(nextId);
        System.out.println("生成的ID转时间戳："+timeMillis);

        LocalDateTime localDateTime = LocalDateTimeUtil.convertMillisToLocalDateTime(timeMillis);
        System.out.println("生成的ID转LocalDateTime："+localDateTime);

        String localDateTimeToString = LocalDateTimeUtil.convertLocalDateTimeToString(localDateTime, "yyyy-MM-dd HH:mm:ss");
        System.out.println("生成的ID转localDateTimeToString："+localDateTimeToString);
    }
}
