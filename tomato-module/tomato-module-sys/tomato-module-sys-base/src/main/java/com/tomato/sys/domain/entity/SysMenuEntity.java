package com.tomato.sys.domain.entity;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;

/**
 * 菜单表
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@Data
public class SysMenuEntity extends BaseEntity {
    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 类型
     *
     * @see MenuTypeEnum
     */
    private Integer menuType;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否为外链
     */
    private Boolean frameFlag;

    /**
     * 外链地址
     */
    private String frameUrl;

    /**
     * 是否缓存
     */
    private Boolean cacheFlag;

    /**
     * 显示状态
     */
    private Boolean visibleFlag;

    /**
     * 禁用状态
     */
    private Boolean disabledFlag;

    /**
     * 后端权限字符串
     */
    private String apiPerms;

    /**
     * 权限类型
     */
    private Integer permsType;

    /**
     * 前端权限字符串
     */
    private String webPerms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 功能点关联菜单ID
     */
    private Long contextMenuId;

    /**
     * 删除状态
     */
    private Boolean deletedFlag;

    /**
     * 创建人
     */
    private Long createUserId;

    /**
     * 更新人
     */
    private Long updateUserId;
}
