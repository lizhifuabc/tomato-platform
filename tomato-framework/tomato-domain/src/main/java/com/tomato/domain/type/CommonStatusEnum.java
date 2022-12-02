package com.tomato.domain.type;

/**
 * 通用状态
 *
 * @author lizhifu
 * @date 2022/12/2
 */
public enum CommonStatusEnum implements BaseEnum{
    /**
     * 初始化
     */
    INIT("INIT", "初始化"),
    /**
     * 处理中
     */
    DEAL("DEAL", "处理中"),
    /**
     * 成功
     */
    SUCCESS("SUCCESS", "成功"),
    /**
     * 失败
     */
    FAIL("FAIL", "失败"),
    /**
     * 取消
     */
    CANCEL("CANCEL", "取消"),
    ;

    CommonStatusEnum(String value,String desc) {
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
    private final String value;

    private final String desc;
}
