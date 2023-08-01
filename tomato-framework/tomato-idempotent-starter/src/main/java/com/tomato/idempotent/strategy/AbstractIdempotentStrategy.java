package com.tomato.idempotent.strategy;

/**
 * 抽象幂等执行策略
 *
 * @author lizhifu
 * @since 2023/8/1
 */
public abstract class AbstractIdempotentStrategy implements IdempotentStrategy {
    /**
     * 生成请求key
     * @param sign 签名
     * @return 请求key
     */
    protected String generateRequestKey(String sign) {
        return sign;
    }
}
