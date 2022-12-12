package com.tomato.order;

/**
 * 基因法
 *
 * @author lizhifu
 * @date 2022/12/11
 */
public class ModIdTest {
    public static void main(String[] args) {
        // 二进制到十进制的转换：Integer.parseInt()
        // Integer.toBinaryString(n);

        //分片数量
        int userID = 20160169;
        String bit = Integer.toBinaryString(userID);
        String xbit = bit.substring(bit.length() -4);
        System.out.println(xbit);

        long snowId = 1595662702879973377L;
        String snowIdBit = Long.toBinaryString(snowId);

        String nSnowIdBit = snowIdBit + xbit;
        System.out.println("雪花算法生成的订单ID为:"+Integer.parseInt(nSnowIdBit));

//        Long orderId = buildGenId(snowId,gen);
//        System.out.println("基因转换后的订单ID为：1595662702879973385");
//        System.out.println("orderId % shardNum："+orderId % shardNum);
//        System.out.println("userID % shardNum："+userID % shardNum);
    }
}
