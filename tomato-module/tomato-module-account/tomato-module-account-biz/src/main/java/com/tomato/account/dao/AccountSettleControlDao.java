package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountSettleControlEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账户结算控制
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Mapper
public interface AccountSettleControlDao {
    /**
     * 插入
     * @param accountSettleControlEntity 账户结算控制
     */
    public void insert(AccountSettleControlEntity accountSettleControlEntity);
}
