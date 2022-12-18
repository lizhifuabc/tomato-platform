package com.tomato.sys.user.manager;

import com.tomato.sys.domain.entity.SysUserEntity;
import com.tomato.sys.user.dao.SysUserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@Service
public class SysUserManager {
    @Value("${tomato.security.password:123456}")
    private String password;
    private final PasswordEncoder passwordEncoder;
    private final SysUserDao sysUserDao;

    public SysUserManager(PasswordEncoder passwordEncoder, SysUserDao sysUserDao) {
        this.passwordEncoder = passwordEncoder;
        this.sysUserDao = sysUserDao;
    }
    @Transactional(rollbackFor = Throwable.class)
    public void createSysUser(SysUserEntity sysUserEntity, List<Long> roleIdList) {
        // 默认密码
        sysUserEntity.setLoginPwd(passwordEncoder.encode(password));
        sysUserDao.insert(sysUserEntity);
        // TODO 角色
    }
}
