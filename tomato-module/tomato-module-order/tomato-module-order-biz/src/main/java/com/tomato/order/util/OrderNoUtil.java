package com.tomato.order.util;

import com.tomato.util.StrUtil;
import com.tomato.util.date.DatePattern;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 订单编号生成策略
 * 订单号 = 时间戳14位|分库编号2位|分表编号2位|5位随机数
 * 221220-545353963062231
 * 时间戳+用户标识码+随机数
 * 1672579493068
 * @author lizhifu
 * @date 2022/12/1
 */
public class OrderNoUtil {

    public static String genOrderNo(String merchantOrderNo,String merchantNo){
        long dbSuffix = HashShardingValueUtil.dbSuffix(merchantOrderNo,merchantNo,2);
        long tableSuffix = HashShardingValueUtil.tableSuffix(merchantOrderNo,merchantNo,2);
        String no = LocalDateTime.now().format(DatePattern.PURE_DATETIME_FORMATTER) +
                StrUtil.fillAfter("0", String.valueOf(dbSuffix), 2) +
                StrUtil.fillAfter("0", String.valueOf(tableSuffix), 2);
        String ran = StrUtil.fillAfter("0", String.valueOf(ThreadLocalRandom.current().nextLong(1, 10000)), 5);
        return no + ran;
    }

    public static void main(String[] args) {
        String merchantOrderNo = UUID.randomUUID().toString();
        String merchantNo = "123123121";

        System.out.println(genOrderNo(merchantOrderNo, merchantNo));
    }
}
