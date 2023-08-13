package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountWorkEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 节假日控制
 *
 * @author lizhifu
 * @since 2023/1/10
 */
@Mapper
public interface AccountWorkDao {
    /**
     * 插入
     * @param accountWorkEntity 节假日控制
     */
    void insert(AccountWorkEntity accountWorkEntity);

    /**
     * 查询
     * @param workDay 日期
     * @return
     */
    List<AccountWorkEntity> selectByWorkDay(@Param("workDay")LocalDate workDay);
}
