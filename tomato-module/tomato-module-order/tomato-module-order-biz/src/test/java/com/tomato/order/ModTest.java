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
        int num = 3;
        String orderNo = UUID.randomUUID().toString();
        int hash = orderNo.hashCode();
        System.out.println(hash);
        System.out.println(hash % num);
    }
}
