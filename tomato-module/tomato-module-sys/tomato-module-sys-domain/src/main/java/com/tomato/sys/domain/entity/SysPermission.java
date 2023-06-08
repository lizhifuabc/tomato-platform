package com.tomato.sys.domain.entity;

import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

/**
 * 系统菜单
 * @author lizhifu
 */
@Entity
@Table(name = "t_sys_permission")
public class SysPermission extends BaseSysEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "permission_id")
    @Comment("菜单ID")
    private Long permissionId;
    @Schema(name = "权限代码")
    @Column(name = "permission_code", length = 128)
    private String permissionCode;

    @Schema(name = "权限名称")
    @Column(name = "permission_name", length = 1024)
    private String permissionName;
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysPermission that = (SysPermission) o;
        return Objects.equal(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(permissionId);
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
