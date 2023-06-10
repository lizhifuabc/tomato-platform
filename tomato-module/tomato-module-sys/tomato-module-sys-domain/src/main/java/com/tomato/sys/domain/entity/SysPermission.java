package com.tomato.sys.domain.entity;

import com.google.common.base.Objects;
import com.tomato.sys.domain.enums.PermissionTypeEnum;
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
    @Enumerated(EnumType.ORDINAL)
    @Comment("权限类型")
    @Column(name = "permission_type", length = 2, nullable = false)
    public PermissionTypeEnum permissionType;
    @Column(name = "parent_id", nullable = false)
    @Comment("父菜单ID")
    private Long parentId = 0L;
    @Column(name = "sort", nullable = false)
    @Comment("排序")
    private Integer sort = 0;
    @Column(name = "visible_flag", nullable = false, columnDefinition = "bit(1) default 1")
    @Comment("显示状态")
    private Boolean visibleFlag;
    @Column(name = "disabled_flag", nullable = false, columnDefinition = "bit(1) default 0")
    @Comment("禁用状态")
    private Boolean disabledFlag;
    @Column(name = "frame_flag", nullable = false, columnDefinition = "bit(1) default 0")
    @Comment("是否为外链")
    private Boolean frameFlag;
    @Column(name = "frame_url", length = 1024)
    @Comment("外链地址")
    private String frameUrl;
    @Column(name = "path", length = 1024)
    @Comment("路由地址")
    private String path;
    @Column(name = "component", length = 1024)
    @Comment("组件路径")
    private String component;
    @Column(name = "icon", length = 128)
    @Comment("菜单图标")
    private String icon;
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

    public PermissionTypeEnum getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionTypeEnum permissionType) {
        this.permissionType = permissionType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getVisibleFlag() {
        return visibleFlag;
    }

    public void setVisibleFlag(Boolean visibleFlag) {
        this.visibleFlag = visibleFlag;
    }

    public Boolean getDisabledFlag() {
        return disabledFlag;
    }

    public void setDisabledFlag(Boolean disabledFlag) {
        this.disabledFlag = disabledFlag;
    }

    public Boolean getFrameFlag() {
        return frameFlag;
    }

    public void setFrameFlag(Boolean frameFlag) {
        this.frameFlag = frameFlag;
    }

    public String getFrameUrl() {
        return frameUrl;
    }

    public void setFrameUrl(String frameUrl) {
        this.frameUrl = frameUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
