
package com.tomato.gen.domain.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 表信息
 * @author lizhifu
 */
@Data
public class TableResp {

    @Schema(description = "表名")
    private String tableName;

    @Schema(description = "表名去除前缀转换成驼峰")
    private String upperTableName;

    @Schema(description = "表备注")
    private String tableComment;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}
