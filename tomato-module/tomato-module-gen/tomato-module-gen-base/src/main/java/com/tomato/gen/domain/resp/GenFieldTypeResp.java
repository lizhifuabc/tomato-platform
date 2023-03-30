package com.tomato.gen.domain.resp;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 表：t_gen_field_type 字段类型管理 resp
 *
 * @author tomato
 * @since  2023-03-30T10:46:16.710799
 */
@Data
@Schema(description = "字段类型管理")
public class GenFieldTypeResp {
    /**
     * 字段类型 data_type
     */
    @Schema(description = "字段类型")
    private String dataType;
    /**
     * 属性类型 attr_type
     */
    @Schema(description = "属性类型")
    private String attrType;
    /**
     * 属性包名 package_name
     */
    @Schema(description = "属性包名")
    private String packageName;
    /**
     *  id
     */
    @Schema(description = "")
    private Long id;
    /**
     * 乐观锁 version
     */
    @Schema(description = "乐观锁")
    private Integer version;
    /**
     * 更新时间 update_time
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 创建时间 create_time
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
