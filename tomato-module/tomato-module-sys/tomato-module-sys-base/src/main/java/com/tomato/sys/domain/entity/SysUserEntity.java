package com.tomato.sys.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

/**
 * 用户信息
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@Data
public class SysUserEntity extends BaseEntity {
    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 用户姓名
     */
    private String actualName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 是否被禁用 0否1是
     */
    private Boolean disabledFlag;
    /**
     * 备注
     */
    private String remark;
}
