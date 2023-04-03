package com.tomato.demo.infrastructure.userlog.mapper;

import com.tomato.demo.infrastructure.userlog.entity.UserLogEntity;
import org.springframework.stereotype.Repository;

/**
 * 用户日志表
 *
 * @author lizhifu
 * @since 2023/4/2
 */
@Repository
public interface UserLogEntityMapper {
    /**
     * 插入
     * @param record 用户日志表
     * @return int 影响行数
     */
    int insert(UserLogEntity record);
}
