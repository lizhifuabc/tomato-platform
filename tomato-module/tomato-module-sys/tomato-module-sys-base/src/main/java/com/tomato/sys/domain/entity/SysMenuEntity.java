package com.tomato.sys.domain.entity;

import com.tomato.domain.entity.BaseEntity;
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
     * 权限标识
     */
    private String permission;

    /**
     * 类型
     */
    private Integer menuType;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 显示状态
     */
    private Boolean visibleFlag;

    /**
     * 是否缓存
     */
    private Boolean cacheFlag;

    /**
     * 是否禁用
     */
    private Boolean disabledFlag;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 更新者
     */
    private String updater;
}
