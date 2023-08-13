package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountManageHisEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账户管理历史表
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@Mapper
public interface AccountManageHisDao {
    /**
     * 插入
     * @param accountManageHisEntity 账户管理历史表
     */
    public void insert(AccountManageHisEntity accountManageHisEntity);
}
