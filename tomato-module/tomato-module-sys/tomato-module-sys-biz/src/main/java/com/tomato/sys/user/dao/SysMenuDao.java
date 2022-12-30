package com.tomato.sys.user.dao;

import com.tomato.sys.domain.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单表
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@Mapper
public interface SysMenuDao {
    /**
     * 查询所有
     * @return 菜单集合
     */
    public List<SysMenuEntity> select();
}
