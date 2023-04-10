package com.tomato.sys.constant;

import com.tomato.domain.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库类型
 *
 * @author lizhifu
 * @since 2023/1/17
 */
@AllArgsConstructor
@Getter
public enum DbTypeEnum implements BaseEnum {
    /**
     * MySQL
     */
    MySQL("com.mysql.cj.jdbc.Driver","MySQL"),
    ;
    /**
     * value
     */
    private final String value;

    private final String desc;

}
