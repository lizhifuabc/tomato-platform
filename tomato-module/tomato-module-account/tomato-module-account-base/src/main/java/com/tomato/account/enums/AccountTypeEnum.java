package com.tomato.account.enums;

import com.tomato.domain.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户类型枚举
 *
 * @author lizhifu
 * @since  2022/12/2
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
