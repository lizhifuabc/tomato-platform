package com.tomato.idempotent.strategy;

import com.tomato.idempotent.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;

/**
 * 幂等执行策略
 *
 * @author lizhifu
 * @since 2023/4/11
 */
public interface IdempotentStrategy {
    /**
     * 幂等执行
     * @param joinPoint 切点
     * @param idempotent 幂等注解
     */
    void execute(JoinPoint joinPoint, Idempotent idempotent);
    /**
     * 策略名称
     * @return 策略名称
     */
    String name();
}
