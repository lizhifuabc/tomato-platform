package com.tomato.sys.user.dao;

import com.tomato.sys.domain.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户信息
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@Mapper
public interface SysUserDao {
    /**
     * 根据 ID 查询
     * @param id id
     * @return 用户信息
     */
    public SysUserEntity selectById(@Param("id") Long id);
}
