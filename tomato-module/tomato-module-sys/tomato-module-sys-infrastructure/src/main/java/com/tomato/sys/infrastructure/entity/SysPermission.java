package com.tomato.sys.infrastructure.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.tomato.sys.infrastructure.base.BaseSysEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;


/**
 * 系统权限实体
 * @author lizhifu
 */
@Entity
@Table(name = "sys_permission")
public class SysPermission extends BaseSysEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "permission_id")
    @Comment("权限ID")
    private Long permissionId;

    @Column(name = "permission_code", length = 128)
    @Comment("权限编码")
    private String permissionCode;

    @Column(name = "permission_name", length = 1024)
    @Comment("权限名称")
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
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("permissionId", permissionId)
                .add("permissionCode", permissionCode)
                .add("permissionName", permissionName)
                .toString();
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
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
}
