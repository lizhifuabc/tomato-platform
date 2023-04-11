package com.tomato.sys.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.tomato.sys.infrastructure.base.BaseSysEntity;
import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统用户
 * @author lizhifu
 */
@Entity
@Table(name = "t_sys_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})},
        indexes = {@Index(name = "sys_user_id_idx", columnList = "user_id"), @Index(name = "sys_user_unm_idx", columnList = "user_name")})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId") // 避免循环引用
public class SysUser extends BaseSysEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Comment("用户ID")
    private Long userId;

    @Comment("用户名")
    @Column(name = "user_name", length = 128, unique = true)
    private String userName;

    @Comment("密码")
    @Column(name = "password", length = 256)
    private String password;

    @Comment("昵称")
    @Column(name = "nick_name", length = 64)
    private String nickName;

    @Comment("手机号码")
    @Column(name = "phone_number", length = 256)
    private String phoneNumber;

    @Comment("头像")
    @Column(name = "avatar", length = 1024)
    private String avatar;

    @Comment("EMAIL")
    @Column(name = "email", length = 100)
    private String email;

    @Comment("账户过期日期")
    @Column(name = "account_expire_at")
    private LocalDateTime accountExpireAt;

    @Comment("密码过期日期")
    @Column(name = "credentials_expire_at")
    private LocalDateTime credentialsExpireAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "t_sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = {@JoinColumn(name = "role_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})},
            indexes = {@Index(name = "sys_user_role_uid_idx", columnList = "user_id"), @Index(name = "sys_user_role_rid_idx", columnList = "role_id")})
    private Set<SysRole> roles = new HashSet<>();
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getAccountExpireAt() {
        return accountExpireAt;
    }

    public void setAccountExpireAt(LocalDateTime accountExpireAt) {
        this.accountExpireAt = accountExpireAt;
    }

    public LocalDateTime getCredentialsExpireAt() {
        return credentialsExpireAt;
    }

    public void setCredentialsExpireAt(LocalDateTime credentialsExpireAt) {
        this.credentialsExpireAt = credentialsExpireAt;
    }

    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }
}
