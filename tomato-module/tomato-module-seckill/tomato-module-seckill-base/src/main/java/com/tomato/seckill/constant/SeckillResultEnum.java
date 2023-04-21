package com.tomato.seckill.constant;

import com.tomato.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 秒杀结果
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@AllArgsConstructor
@Getter
public enum SeckillResultEnum implements BaseEnum {
    /**
     * 排队中
     */
    DEAL("DEAL", "排队中"),
    /**
     * 成功
     */
    SUCCESS("SUCCESS", "成功"),
    /**
     * 失败
     */
    FAIL("FAIL", "失败"),
    ;
    private final String value;

    private final String desc;
}
