package com.tomato.order;

import java.util.UUID;

/**
 * t_order_info
 *
 * @author lizhifu
 * @date 2022/12/7
 */
public class ModTest {
    public static void main(String[] args) {
        // 数量
        int num = 3;
        String key = UUID.randomUUID().toString();
        // hash值：返回一个数的绝对值
        long hash = Math.abs((long) key.hashCode());
        System.out.println(hash % num);
    }
}
