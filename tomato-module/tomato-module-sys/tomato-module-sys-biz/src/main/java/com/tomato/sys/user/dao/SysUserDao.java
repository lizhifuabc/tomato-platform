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
    SysUserEntity selectById(@Param("id") Long id);

    /**
     * 通过登录名查询是否存在
     *
     * @param loginName 登录名
     * @return 用户信息
     */
    Boolean existByLoginName(@Param("loginName") String loginName);
    /**
     * 通过手机号查询是否存在
     *
     * @param phone 手机号
     * @return 用户信息
     */
    Boolean existByPhone(@Param("phone") String phone);
}
