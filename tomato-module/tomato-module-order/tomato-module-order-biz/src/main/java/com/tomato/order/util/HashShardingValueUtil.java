package com.tomato.order.util;

import java.util.Optional;

/**
 * HashShardingValueUtil
 *
 * @author lizhifu
 * @since 2023/1/3
 */
public class HashShardingValueUtil {
    public static long tableSuffix(String merchantOrderNo,String merchantNo,int count) {
        return Math.abs((long) (merchantNo + merchantOrderNo).hashCode()) % count;
    }
    public static long dbSuffix(String merchantOrderNo,String merchantNo,int count) {
        return Math.abs((long) (merchantNo + merchantOrderNo).hashCode()) % count;
    }
    public static String tableIndex(String orderNo){
        return Optional.ofNullable(orderNo)
                .filter(s -> s.length() >= 4)
                .map(s -> s.substring(s.length() - 4,s.length() - 2))
                .orElse("0");
    }
    public static String dbIndex(String orderNo){
        return Optional.ofNullable(orderNo)
                .filter(s -> s.length() >= 4)
                .map(s -> s.substring(s.length() - 4,s.length() - 2))
                .orElse("0");
    }
}
