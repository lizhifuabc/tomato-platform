package com.tomato.reconciliation.enums;

import com.tomato.common.enums.BaseEnum;

/**
 * 单边类型
 * @author lizhifu
 */
public enum UnilateralType implements BaseEnum<String> {
    /**
     * 报警单边,核心标识(例如ID)一致，但是明细数据不一致
     */
    ALARM_ERROR("ALARM_ERROR","报警单边"),
    /**
     * 上游单边
     */
    UP_ERROR("UP_ERROR","上游单边"),
    /**
     * 下游单边
     */
    DOWN_ERROR("DOWN_ERROR","下游单边");
    private final String value;

    private final String desc;

    UnilateralType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getValue() {
        return value;
    }
}
