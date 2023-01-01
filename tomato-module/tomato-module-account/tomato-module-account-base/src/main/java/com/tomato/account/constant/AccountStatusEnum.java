package com.tomato.account.constant;

import com.tomato.domain.type.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入账状态枚举
 *
 * @author lizhifu
 * @date 2022/6/25
 */
@AllArgsConstructor
@Getter
public enum AccountStatusEnum implements BaseEnum {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 处理中
     */
    DEAL(100, "处理中"),
    ;
    private final Integer value;

    private final String desc;
}
