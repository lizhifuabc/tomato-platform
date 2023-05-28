package com.tomato.reconciliation.enums;

import com.tomato.common.enums.BaseEnum;

/**
 * 系统上下游标识值
 *
 * @author lizhifu
 * @since 2023/5/28
 */
public enum TaskSysType implements BaseEnum<String> {
    /**
     * 上游
     */
    UP("UP","上游"),
    /**
     * 下游
     */
    DOWN("DOWN","下游");
    private final String value;

    private final String desc;

    TaskSysType(String value, String desc) {
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
