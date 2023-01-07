package com.tomato.util.date;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * {@link LocalDateTime} 工具类封装
 *
 * @author lizhifu
 * @since 2023/1/1
 */
public class LocalDateTimeUtil {
    public static LocalDateTime timestampToDatetime(long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
    public static long dataTimeToTimestamp(LocalDateTime ldt){
        long timestamp = ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return timestamp;
    }
    public void getEndOfDay(LocalDateTime localDateTime) {
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        String endTimeStr = endOfDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("结束时间" + endTimeStr);
    }

    public void getStartOfDay(LocalDateTime localDateTime) {
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        String startTimeStr = startOfDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("开始时间" + startTimeStr);
    }
}
