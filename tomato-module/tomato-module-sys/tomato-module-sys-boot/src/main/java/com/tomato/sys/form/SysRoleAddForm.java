package com.tomato.sys.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 角色添加
 *
 * @author lizhifu
 */
@Data
public class SysRoleAddForm {

	/**
	 * 角色名称
	 */
	@Schema(description = "角色名称")
	@NotNull(message = "角色名称不能为空")
	@Length(min = 1, max = 20, message = "角色名称(1-20)个字符")
	private String roleName;

	/**
	 * 角色描述
	 */
	@Schema(description = "角色描述")
	@Length(max = 255, message = "角色描述最多255个字符")
	private String remark;

}
