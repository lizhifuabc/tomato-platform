package com.tomato.sys.domain.entity;

import com.google.common.base.Objects;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

/**
 * 系统菜单
 * @author lizhifu
 */
@Entity
@Table(name = "t_sys_menu")
public class SysMenu extends BaseSysEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "menu_id")
    @Comment("菜单ID")
    private Long menuId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysMenu that = (SysMenu) o;
        return Objects.equal(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(menuId);
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
