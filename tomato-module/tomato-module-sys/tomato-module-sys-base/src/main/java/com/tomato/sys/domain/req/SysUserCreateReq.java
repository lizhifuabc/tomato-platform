package com.tomato.sys.domain.req;

import com.tomato.sys.constant.GenderEnum;
import com.tomato.validator.annotation.CheckEnum;
import com.tomato.validator.validator.VerificationPattern;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 添加用户
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@Data
public class SysUserCreateReq {
    @NotNull(message = "姓名不能为空")
    @Length(max = 30, message = "姓名最多30字符")
    private String actualName;

    @NotNull(message = "登录账号不能为空")
    @Length(max = 30, message = "登录账号最多30字符")
    private String loginName;

    @CheckEnum(value = GenderEnum.class, message = "性别错误")
    private Integer gender;

    @NotNull(message = "部门id不能为空")
    private Long departmentId;

    @NotNull(message = "是否被禁用不能为空")
    private Boolean disabledFlag;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = VerificationPattern.PHONE_REGEXP, message = "手机号格式不正确")
    private String phone;
    @Length(max = 200, message = "备注最多200字符")
    private String remark;
    private List<Long> roleIdList;
}
