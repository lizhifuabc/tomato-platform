package com.tomato.sys.infrastructure.entity;

import com.tomato.sys.infrastructure.base.BaseSysEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

/**
 * 系统菜单
 */
@Entity
@Table(name = "sys_menu")
public class SysMenu extends BaseSysEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "menu_id")
    @Comment("菜单ID")
    private Long menuId;

    @Column(name = "menu_name", length = 50)
    @Comment("菜单名称")
    private String menuName;
    /**
     * 权限标识
     *
     * 一般格式为：${系统}:${模块}:${操作}
     * 例如说：system:admin:add，即 system 服务的添加管理员。
     *
     * 当我们把该 MenuDO 赋予给角色后，意味着该角色有该资源：
     * - 对于后端，配合 @PreAuthorize 注解，配置 API 接口需要该权限，从而对 API 接口进行权限控制。
     * - 对于前端，配合前端标签，配置按钮是否展示，避免用户没有该权限时，结果可以看到该操作。
     */
    @Column(name = "menu_permission", length = 100)
    @Comment("菜单名称")
    private String menuPermission;
    /**
     * 菜单类型
     *
     * 枚举 {@link MenuTypeEnum}
     */
    private Integer type;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 父菜单ID
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
     * 状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 是否可见
     *
     * 只有菜单、目录使用
     * 当设置为 true 时，该菜单不会展示在侧边栏，但是路由还是存在。例如说，一些独立的编辑页面 /edit/1024 等等
     */
    private Boolean visible;
}
