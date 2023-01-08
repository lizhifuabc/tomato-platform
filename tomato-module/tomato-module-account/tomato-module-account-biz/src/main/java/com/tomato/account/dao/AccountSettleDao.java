package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountSettleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账户结算规则
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Mapper
public interface AccountSettleDao {

    /**
     * 插入
     * @param accountSettleEntity 账户结算规则
     */
    public void insert(AccountSettleEntity accountSettleEntity);
}
