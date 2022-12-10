package com.tomato.order;

import com.tomato.order.domain.entity.OrderEntity;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;

import java.util.Optional;

/**
 * Test
 *
 * @author lizhifu
 * @date 2022/12/5
 */
public class Test {
    private static long runTimeout;
    public static void main(String[] args) {
        System.out.println(runTimeout);
        System.out.println(System.nanoTime());
        System.out.println(System.currentTimeMillis());

        Optional<OrderEntity> order = Optional.empty();
        System.out.println(order.isPresent());
        String s = order
                .map(OrderEntity::getNoticeWeb)
                .map(String::toUpperCase).orElse(null);
        System.out.println(s);

        String a = "null";
        System.out.println(up(a));


        OrderEntity orderEntity = null;
//        orderEntity.setNoticeWeb(null);
        Optional.ofNullable(orderEntity).map(OrderEntity::getNoticeWeb).map(String::toUpperCase).ifPresent(orderEntity::setNoticeWeb);
        System.out.println("UP:"+orderEntity.getNoticeWeb());
    }

    public static String up(String source){
        return Optional.ofNullable(source).map(String::toUpperCase).orElse(source);
    }
}
