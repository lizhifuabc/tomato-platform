package com.tomato.auth.server.sys;

import com.tomato.auth.server.sys.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * UsersMapper
 *
 * @author lizhifu
 * @since 2023/4/4
 */
@Mapper
public interface SysUserEntityMapper {
    @Select("select * from users where username = #{username}")
    SysUserEntity selectByUsername(@Param("username") String username);
}
