package com.tomato.sys.application.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tomato.sys.domain.enums.LoginDeviceEnum;
import com.tomato.validator.annotation.CheckEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 登录
 *
 * @author lizhifu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLoginReq extends CaptchaReq {

	@Schema(description = "登录名")
	@NotBlank(message = "登录名不能为空")
	@Length(max = 30, message = "登录账号最多30字符")
	private String username;

	@Schema(description = "密码")
	@NotBlank(message = "密码不能为空")
	private String password;

	@Schema(description = "登录终端")
	@CheckEnum(value = LoginDeviceEnum.class, required = true, message = "此终端不允许登录")
	private Integer loginDevice;

	@Override
	public String toString() {
		return "SysLoginReq{" + "username='" + username + '\'' + ", loginDevice=" + loginDevice + '}'
				+ super.toString();
	}

}
