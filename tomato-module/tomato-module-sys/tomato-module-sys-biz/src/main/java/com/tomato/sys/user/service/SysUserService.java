package com.tomato.sys.user.service;

import com.tomato.domain.resp.Resp;
import com.tomato.common.constants.CommonRespCode;
import com.tomato.sys.domain.entity.SysUserEntity;
import com.tomato.sys.domain.req.SysUserCreateReq;
import com.tomato.sys.user.dao.SysUserDao;
import com.tomato.sys.user.manager.SysUserManager;
import com.tomato.web.util.BeanUtil;
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
    private final SysUserManager sysUserManager;
    public SysUserService(SysUserDao sysUserDao, SysUserManager sysUserManager) {
        this.sysUserDao = sysUserDao;
        this.sysUserManager = sysUserManager;
    }

    /**
     * 创建用户
     * @param sysUserCreateReq 创建用户信息
     */
    public Resp createSysUser(SysUserCreateReq sysUserCreateReq) {
        // 校验登录账号
        if (sysUserDao.existByLoginName(sysUserCreateReq.getLoginName())){
            return Resp.buildFailure(CommonRespCode.FAIL.code(),"登录账号已存在");
        }
        // 校验手机号码
        if (sysUserDao.existByPhone(sysUserCreateReq.getPhone())){
            return Resp.buildFailure(CommonRespCode.FAIL.code(),"手机号码已存在");
        }
        // 部门是否存在 TODO
        // 保存数据
        SysUserEntity sysUserEntity = BeanUtil.copy(sysUserCreateReq,SysUserEntity.class);
        sysUserManager.createSysUser(sysUserEntity,sysUserCreateReq.getRoleIdList());
        return Resp.buildSuccess();
    }
}
