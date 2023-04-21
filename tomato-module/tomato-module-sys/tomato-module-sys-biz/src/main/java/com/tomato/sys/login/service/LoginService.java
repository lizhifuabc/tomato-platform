package com.tomato.sys.login.service;

import com.tomato.domain.resp.Resp;
import com.tomato.security.enums.LoginDeviceEnum;
import com.tomato.security.token.TokenService;
import com.tomato.sys.domain.entity.SysMenuEntity;
import com.tomato.sys.domain.entity.SysUserEntity;
import com.tomato.sys.domain.req.LoginReq;
import com.tomato.sys.domain.resp.LoginResp;
import com.tomato.sys.domain.resp.SysMenuResp;
import com.tomato.sys.login.domain.bo.LoginUserDetails;
import com.tomato.sys.user.dao.SysMenuDao;
import com.tomato.sys.user.dao.SysUserDao;
import com.tomato.web.util.BeanUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录服务
 *
 * @author lizhifu
 * @since 2022/12/16
 */
@Service
@Slf4j
public class LoginService {
    private final TokenService tokenService;
    private final SysUserDao sysUserDao;
    private final PasswordEncoder passwordEncoder;
    private final SysMenuDao sysMenuDao;
    public LoginService(TokenService tokenService, SysUserDao sysUserDao, PasswordEncoder passwordEncoder, SysMenuDao sysMenuDao) {
        this.tokenService = tokenService;
        this.sysUserDao = sysUserDao;
        this.passwordEncoder = passwordEncoder;
        this.sysMenuDao = sysMenuDao;
    }

    public Resp<LoginResp> login(LoginReq loginReq, String ip) {
        SysUserEntity sysUserEntity = sysUserDao.selectByLoginName(loginReq.getLoginName());
        // 校验账号是否存在
        if(null == sysUserEntity){
            return Resp.buildFailure("登录失败，账号或密码不正确");
        }
        // 校验密码
        if(!passwordEncoder.matches(loginReq.getLoginPwd(), sysUserEntity.getLoginPwd())){
            return Resp.buildFailure("登录失败，账号或密码不正确");
        }
        // 校验是否禁用
        if (sysUserEntity.getDisabledFlag()) {
            return Resp.buildFailure("登录失败，账号已被禁用");
        }
        String token = tokenService.generateToken(sysUserEntity.getId(),loginReq.getLoginName(), LoginDeviceEnum.PC);
        List<SysMenuEntity> select = sysMenuDao.select();
        // TODO 菜单角色权限
        List<SysMenuResp> sysMenuRespList = BeanUtil.copyList(select,SysMenuResp.class);
        LoginResp loginResp = LoginResp.builder()
                .token(token)
                .menuList(sysMenuRespList)
                .build();
        return Resp.of(loginResp);
    }

    /**
     * 根据 token 获取用户信息
     *
     * @param token
     * @param request
     * @return
     */
    public UserDetails getLoginUserDetail(String token, HttpServletRequest request) {

        Long userId = tokenService.getUserId(token);
        SysUserEntity sysUserEntity = sysUserDao.selectById(userId);
        LoginUserDetails loginResp = LoginUserDetails.builder()
                .token(token)
                .authorities(buildAuthorities())
                .loginName(sysUserEntity.getLoginName())
                .build();
        log.info("loginResp is {}",loginResp);
        return loginResp;
    }

    private Set<? extends GrantedAuthority> buildAuthorities() {
        HashSet<String> permissionList = new HashSet<>();
        permissionList.add("/login/logout");
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(permissionList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        return authorities;
    }
}
