package com.tomato.sys.user.service;

import com.tomato.sys.domain.req.SysUserCreateReq;
import com.tomato.sys.user.dao.SysUserDao;
import org.springframework.stereotype.Service;

/**
 * 用户
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@Service
public class SysUserService {
    private final SysUserDao sysUserDao;

    public SysUserService(SysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }

    /**
     * 创建用户
     * @param sysUserCreateReq 创建用户信息
     */
    public void createSysUser(SysUserCreateReq sysUserCreateReq) {
        // 校验登录账号
        // 校验手机号码
        // 部门是否存在 TODO
        // 默认密码
        // 保存数据
    }
}
