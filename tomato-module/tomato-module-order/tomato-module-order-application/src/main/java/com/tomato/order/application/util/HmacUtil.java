package com.tomato.order.application.util;

import com.google.common.collect.ImmutableSortedMap;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.tomato.order.application.req.OrderCreateReq;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * hmac 工具类
 *
 * @author lizhifu
 * @since 2023/8/1
 */
public class HmacUtil {
    /**
     * 生成 hmac
     * @param orderCreateReq 订单信息
     * @param key 密钥
     * @return hmac 值
     */
    public static String hmac(OrderCreateReq orderCreateReq,String key){
        // 获取实体类的字段数组
        Field[] fields = OrderCreateReq.class.getDeclaredFields();
        // 使用 ImmutableSortedMap 按字段名字母顺序排序字段
        ImmutableSortedMap<String, Field> sortedFields = ImmutableSortedMap.<String, Field>naturalOrder()
                .putAll(Arrays.stream(fields)
                        .filter(field -> !Objects.equals(field.getName(), "hmac"))
                        .collect(Collectors.toMap(Field::getName, field -> field)))
                .build();
        // 拼接排序后的字段值
        String original = sortedFields.values().stream()
                .peek(field -> field.setAccessible(true))
                .map(field -> {
                    try {
                        return field.get(orderCreateReq);
                    } catch (IllegalAccessException ignored) {
                    }
                    return "";
                }).filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining("&"));
        HashCode hashCode = Hashing.hmacSha512(key.getBytes(StandardCharsets.UTF_8))
                .hashBytes(original.getBytes(StandardCharsets.UTF_8));
        return hashCode.toString();
    }
}
