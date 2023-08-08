package com.tomato.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * LocalDateTimeUtil
 *
 * @author lizhifu
 * @since 2023/8/8
 */
public class LocalDateTimeUtilTest {
    public static LocalDateTime convertMillisToLocalDateTime(long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        return LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
    }

    public static void main(String[] args) {
        long millis = System.currentTimeMillis(); // 当前时间的毫秒数
        LocalDateTime localDateTime = convertMillisToLocalDateTime(millis);
        System.out.println("LocalDateTime: " + localDateTime);
    }
}
