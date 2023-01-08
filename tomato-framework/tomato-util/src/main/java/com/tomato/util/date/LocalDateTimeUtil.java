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
    /**
     * 修改为一天的开始时间，例如：2020-02-02 00:00:00,000
     *
     * @param time 日期时间
     * @return 一天的开始时间
     */
    public static LocalDateTime beginOfDay(LocalDateTime time) {
        return time.with(LocalTime.MIN);
    }

    /**
     * 修改为一天的结束时间，例如：2020-02-02 23:59:59,999
     *
     * @param time 日期时间
     * @return 一天的结束时间
     */
    public static LocalDateTime endOfDay(LocalDateTime time) {
        return endOfDay(time, false);
    }

    /**
     * 修改为一天的结束时间，例如：
     * <ul>
     * 	<li>毫秒不归零：2020-02-02 23:59:59,999</li>
     * 	<li>毫秒归零：2020-02-02 23:59:59,000</li>
     * </ul>
     *
     * @param time                日期时间
     * @param truncateMillisecond 是否毫秒归零
     * @return 一天的结束时间
     */
    public static LocalDateTime endOfDay(LocalDateTime time, boolean truncateMillisecond) {
        if (truncateMillisecond) {
            return time.with(LocalTime.of(23, 59, 59));
        }
        return time.with(LocalTime.MAX);
    }
}
