package com.tomato.demo.infrastructure.user.mapper;

import com.tomato.demo.infrastructure.user.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Mapper对象
 *
 * @author lizhifu
 * @since 2023/4/2
 */
@Repository
public interface UserEntityMapper {
    /**
     * 根据主键查询
     * @param id 主键
     * @return UserEntity 对象
     */
    UserEntity selectByPrimaryKey(@Param("id") Long id);
}
