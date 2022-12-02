package com.tomato.domain.type;

/**
 * 是与否
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
    public Object getValue() {
        return null;
    }

    @Override
    public String getDesc() {
        return null;
    }
    private final Integer value;

    private final String desc;
}
