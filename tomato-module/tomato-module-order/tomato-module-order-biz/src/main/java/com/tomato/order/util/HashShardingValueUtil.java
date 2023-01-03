package com.tomato.order.util;

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
//    public String tableIndex(String orderNo){
//
//    }
//    public String dataIndex(String orderNo){
//
//    }
}
