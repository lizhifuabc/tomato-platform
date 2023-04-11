package com.tomato.sys.form;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 角色更新
 * @author lizhifu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleUpdateForm extends SysRoleAddForm {
    /**
     * 角色id
     */
    @Schema(description = "角色id")
    @NotNull(message = "角色id不能为空")
    protected Long roleId;
}
