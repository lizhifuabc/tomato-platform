package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountSettleControlEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

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
    void insert(AccountSettleControlEntity accountSettleControlEntity);

    /**
     * 查询根据下次结算日小于等于指定日期且状态为可结算的结算控制记录
     * @param nextSettleDate 下次结算日期
     * @return 结算控制记录的账号集合
     */
    List<String> selectAccount(@Param("nextSettleDate") LocalDate nextSettleDate);

    /**
     * 查询
     * @param id id
     * @return 实体
     */
    AccountSettleControlEntity selectById(@Param("id") Long id);
}
