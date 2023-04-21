package com.tomato.gen.domain.entity;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 表：t_gen_field_type 字段类型管理 实体
 *
 * @author tomato
 * @since  2023-03-30T10:36:35.936542
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenFieldTypeEntity extends BaseEntity{
    /**
     * 字段类型 data_type
     */
    private String dataType;
    /**
     * 属性类型 attr_type
     */
    private String attrType;
    /**
     * 属性包名 package_name
     */
    private String packageName;
    /**
     *  id
     */
    private Long id;
    /**
     * 乐观锁 version
     */
    private Integer version;
    /**
     * 更新时间 update_time
     */
    private LocalDateTime updateTime;
    /**
     * 创建时间 create_time
     */
    private LocalDateTime createTime;
}
