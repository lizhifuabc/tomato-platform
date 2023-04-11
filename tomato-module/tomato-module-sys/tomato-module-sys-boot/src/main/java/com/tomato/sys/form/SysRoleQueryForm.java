package com.tomato.sys.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色查询
 * extends PageParam TODO
 * @author lizhifu
 */
@Data
public class SysRoleQueryForm {

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色id")
    private String roleId;
}
