package com.tomato.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@AllArgsConstructor
@Getter
public enum CommonStatusEnum implements BaseEnum{
    /**
     * 成功
     */
    SUCCESS("SUCCESS", "成功"),
    /**
     * 处理中
     */
    DEAL("DEAL", "处理中"),

    /**
     * 失败
     */
    FAIL("FAIL", "失败"),

    ;
    private final String value;

    private final String desc;
}
