package com.tomato.domain.core.enums;


/**
 * 通用
 *
 * @author lizhifu
 * @since 2023/1/7
 */
public enum CommonStatusEnum implements BaseEnum<String>{
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

    CommonStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
