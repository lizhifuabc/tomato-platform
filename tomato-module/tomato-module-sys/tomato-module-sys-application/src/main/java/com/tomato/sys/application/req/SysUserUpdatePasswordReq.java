package com.tomato.sys.application.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 修改密码
 *
 * @author lizhifu
 * @since 2023/6/10
 */
@Data
public class SysUserUpdatePasswordReq {

	/**
	 * 旧密码
	 */
	@NotBlank(message = "旧密码不能为空")
	private String oldPassword;

	/**
	 * 新密码
	 */
	@NotBlank(message = "新密码不能为空")
	@Pattern(regexp = "^[A-Za-z0-9.]{6,15}$", message = "请输入6-15位密码(数字|大小写字母|小数点)")
	private String newPassword;

}
