package com.tomato.domain.enums;

/**
 * 是否枚举
 * 0-否, 1-是
 *
 * @author lizhifu
 * @date 2022/12/2
 */
public enum YesNoTypeEnum implements BaseEnum{
    /**
     * 1 是
     */
    YES(1, "是"),

    /**
     * 0 否
     */
    NO(0, "否"),
    ;

    YesNoTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }
    private final Integer value;

    private final String desc;
}
