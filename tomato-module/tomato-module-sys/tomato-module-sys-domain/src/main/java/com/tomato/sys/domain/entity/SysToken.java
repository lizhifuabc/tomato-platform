package com.tomato.sys.domain.entity;

import com.tomato.jpa.domain.entity.BaseEntity;
import com.tomato.sys.domain.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author lizhifu
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_sys_token")
@Entity
public class SysToken extends BaseEntity {
    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public SysUser sysUser;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
    }

    public TokenType getTokenType() {
      return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
      this.tokenType = tokenType;
    }

    public boolean isRevoked() {
      return revoked;
    }

    public void setRevoked(boolean revoked) {
      this.revoked = revoked;
    }

    public boolean isExpired() {
      return expired;
    }

    public void setExpired(boolean expired) {
      this.expired = expired;
    }

    public SysUser getSysUser() {
      return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
      this.sysUser = sysUser;
    }
}
