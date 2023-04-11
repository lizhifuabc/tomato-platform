package com.tomato.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色
 * @author lizhifu
 */
@Data
public class SysRoleVO {

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色备注")
    private String remark;
}
