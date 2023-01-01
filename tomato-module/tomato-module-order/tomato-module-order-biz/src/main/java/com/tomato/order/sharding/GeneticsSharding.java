package com.tomato.order.sharding;

import com.tomato.util.lang.Sequence;

import java.time.LocalDateTime;

/**
 * 基因法分片
 *
 * @author lizhifu
 * @since 2022/12/30
 */
public class GeneticsSharding {
    private static Sequence sequence = new Sequence(null);
    public static void main(String[] args) {
        // 2^n 个库或表
        //
        String merchantNo = "123456789";
        // 时间戳+用户标识码+随机数
        long timestamp = System.currentTimeMillis();



        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        int shardingCount = 1024;
        String suffix = String.valueOf(hashShardingValue(merchantNo) % shardingCount + 1000);
        System.out.println(suffix);
    }
    public static long hashShardingValue(final Comparable<?> shardingValue) {
        return Math.abs((long) shardingValue.hashCode());
    }
    public static long bulidOrderId(long userId) {
        //取用户id后4位
        userId = userId & 15;
        // 先取60位唯一id
        // 使用雪花算法生成60位分布式唯一id：1位符号位+41位时间戳+5位workId+5位datacenterId+6位序列号+4位基因片
        long uniqueId = sequence.nextId();
        //唯一id左移4位、拼接userId后4位
        return (uniqueId << 4) | userId;
    }
}
