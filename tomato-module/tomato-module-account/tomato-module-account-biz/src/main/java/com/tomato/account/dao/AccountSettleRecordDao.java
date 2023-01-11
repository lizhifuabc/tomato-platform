package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountSettleRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 账户结算记录
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Mapper
public interface AccountSettleRecordDao {
    /**
     * 插入
     * @param accountSettleRecordEntity 账户结算记录
     */
    void insert(AccountSettleRecordEntity accountSettleRecordEntity);

    /**
     * 查询
     * @param id id
     * @return
     */
    AccountSettleRecordEntity selectById(@Param("id") Long id);
}
