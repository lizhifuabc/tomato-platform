package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountAsyncEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 异步入账账户
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Mapper
public interface AccountAsyncDao {
    /**
     * 查询
     * @return
     */
    public List<AccountAsyncEntity> select();
}
