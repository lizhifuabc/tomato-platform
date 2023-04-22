package com.tomato.sys.infrastructure.security;

import com.tomato.security.token.TokenService;
import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.infrastructure.repository.SysUserRepository;
import com.tomato.sys.infrastructure.security.user.LoginUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
    private final SysUserRepository sysUserRepository;

    public LoginService(TokenService tokenService, SysUserRepository sysUserRepository) {
        this.tokenService = tokenService;
        this.sysUserRepository = sysUserRepository;
    }
    /**
     * 根据 token 获取用户信息
     *
     * @param token  token
     * @param request request
     * @return 用户信息
     */
    public UserDetails getLoginUserDetail(String token, HttpServletRequest request) {

        Long userId = tokenService.getUserId(token);
        SysUser sysUser = sysUserRepository.findByUserId(userId);
        LoginUserDetails loginResp = LoginUserDetails.builder()
                .token(token)
                .authorities(buildAuthorities())
                .loginName(sysUser.getUserName())
                .build();
        log.info("loginResp is {}",loginResp);
        return loginResp;
    }

    private Set<? extends GrantedAuthority> buildAuthorities() {
        HashSet<String> permissionList = new HashSet<>();
        permissionList.add("/login/logout");
        return permissionList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}
