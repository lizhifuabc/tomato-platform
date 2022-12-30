package com.tomato.sys.login.domain.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tomato.security.domain.LoginUser;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 扩展 spring UserDetails
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@Data
@Builder
public class LoginUserDetails implements UserDetails, LoginUser {
    /**
     * token
     */
    private String token;
    /**
     * 登录密码
     */
    @JsonIgnore
    private String loginPassword;
    /**
     * 登录账号
     */
    private String loginName;
    /**
     * security 权限串
     */
    private Set<? extends GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return this.loginPassword;
    }

    @Override
    public String getUsername() {
        return this.getLoginName();
    }

    /**
     * 账户是否未过期,过期无法验证
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
