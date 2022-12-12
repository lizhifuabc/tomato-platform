package com.tomato.jackson.datamask;

import java.util.function.Function;

/**
 * 数据脱敏
 *
 * @author lizhifu
 * @date 2022/12/12
 */
public enum DataMaskEnum {
    /**
     * 名称
     */
    NAME(s -> s.replaceAll("(\\S)\\S(\\S*)", "$1*$2"))
    ,
    /**
     * 电话
     */
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"))
    ,
    /**
     * 地址
     */
    ADDRESS(s -> s.replaceAll("(\\S{3})\\S{2}(\\S*)\\S{2}", "$1****$2****"))
    ;

    /**
     * 成员变量是一个接口类型
     */
    private Function<String, String> function;

    DataMaskEnum(Function<String, String> function) {
        this.function = function;
    }

    public Function<String, String> function() {
        return this.function;
    }
}
