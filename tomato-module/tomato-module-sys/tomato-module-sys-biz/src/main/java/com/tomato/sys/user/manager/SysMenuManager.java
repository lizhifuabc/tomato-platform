package com.tomato.sys.user.manager;

import com.tomato.sys.domain.entity.SysMenuEntity;
import org.springframework.stereotype.Service;

/**
 * 菜单
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@Service
public interface SysMenuManager {
    /**
     * 插入
     * @param sysMenuEntity 菜单
     */
    void insert(SysMenuEntity sysMenuEntity);
}
