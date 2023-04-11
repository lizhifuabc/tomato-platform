package com.tomato.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 选择角色
 * @author lizhifu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleSelectedVO extends SysRoleVO {

    @Schema(description = "角色名称")
    private Boolean selected;
}
