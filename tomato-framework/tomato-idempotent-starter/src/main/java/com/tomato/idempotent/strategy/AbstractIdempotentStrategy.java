package com.tomato.idempotent.strategy;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * 抽象幂等执行策略
 *
 * @author lizhifu
 * @since 2023/8/1
 */
public abstract class AbstractIdempotentStrategy implements IdempotentStrategy {
    /**
     * MD5加密
     * @param input 签名
     * @return 请求key
     */
    protected String generateRequestKey(String input) {
        return Hashing.md5().hashString(input, StandardCharsets.UTF_8).toString();
    }

    public static void main(String[] args) {
        String input = "中国";
        String s = Hashing.md5().hashString(input, StandardCharsets.UTF_8).toString();
        System.out.println(s);
    }
}
