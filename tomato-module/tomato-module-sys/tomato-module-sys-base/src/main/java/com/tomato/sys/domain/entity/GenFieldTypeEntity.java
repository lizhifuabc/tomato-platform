package com.tomato.sys.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字段类型管理
 *
 * @author lizhifu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GenFieldTypeEntity extends BaseEntity {
    /**
     * 字段类型
     */
    private String columnType;
    /**
     * 属性类型
     */
    private String attrType;
    /**
     * 属性包名
     */
    private String packageName;
}