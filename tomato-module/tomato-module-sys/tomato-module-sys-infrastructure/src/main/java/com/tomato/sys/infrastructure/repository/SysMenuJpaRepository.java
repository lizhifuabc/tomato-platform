package com.tomato.sys.infrastructure.repository;

import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.sys.domain.entity.SysMenu;

/**
 * SysMenuRepository:菜单
 *
 * @author lizhifu
 * @since 2023/4/21
 */
public interface SysMenuJpaRepository extends BaseJpaRepository<SysMenu, String> {
}
