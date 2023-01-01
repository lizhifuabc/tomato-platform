package com.tomato.account.constant;

import com.tomato.domain.type.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户类型枚举
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@AllArgsConstructor
@Getter
public enum AccountTypeEnum implements BaseEnum {
    /**
     * 结算
     */
    SETTLEMENT("SETTLEMENT", "结算"),
    ;

    private final String value;

    private final String desc;
}
