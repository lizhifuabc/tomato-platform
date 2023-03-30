package com.tomato.gen.domain.resp;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 列信息
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@Data
@Schema(description = "表列信息")
public class TableColumnResp {
    @Schema(description = "列名")
    private String columnName;

    @Schema(description = "列名去除前缀转换成驼峰")
    private String lowerColumnName;

    @Schema(description = "列描述")
    private String columnComment;

    @Schema(description = "columnKey")
    private String columnKey;

    @Schema(description = "extra")
    private String extra;

    @Schema(description = "是否为空")
    private String isNullable;

    @Schema(description = "数据类型",example = "varchar")
    private String dataType;

    @Schema(description = "列类型",example = "varchar(50)")
    private String columnType;

    @Schema(description = "java类型",example = "String")
    private String javaType;
}
