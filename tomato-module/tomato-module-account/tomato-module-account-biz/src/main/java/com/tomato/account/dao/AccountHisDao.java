package com.tomato.account.dao;

import com.tomato.account.domain.bo.*;
import com.tomato.account.domain.entity.AccountHisEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 账户历史
 *
 * @author lizhifu
 * @since 2022/12/31
 */
@Mapper
public interface AccountHisDao {
    /**
     * 查询账户历史表  分库分表，需要增加 accountNo,或者定制 accountHisId 也作为分表字段
     * @param accountHisId
     * @return
     */
    AccountHisEntity selectByAccountHisId(@Param("accountHisId") Long accountHisId, @Param("accountNo") String accountNo);

    /**
     * 查询账户历史表
     * @param accountNo
     * @param thirdNo
     * @return
     */
    AccountHisEntity selectByThirdNo(@Param("accountNo") String accountNo,@Param("thirdNo") String thirdNo);

    /**
     * 查询账户历史
     * @param accountNo
     * @param thirdNo
     * @return
     */
    @Select("select count(*) from account_his where account_no = #{accountNo} and third_no = #{thirdNo} limit 1")
    boolean checkThirdNo(@Param("accountNo") String accountNo,@Param("thirdNo") String thirdNo);
    /**
     * 新增账户历史表
     *
     * @param AccountHisEntity 账户历史表
     * @return 新增账户历史表数量
     */
    int insert(AccountHisEntity AccountHisEntity);

    /**
     * 更新账户历史状态  分库分表，需要增加 accountNo,或者定制 accountHisId 也作为分表字段
     * @param AccountHisUpdateBO
     * @return
     */
    int updateAccountStatus(AccountHisUpdateBO AccountHisUpdateBO);

    /**
     * 批量更新账户历史状态  分库分表，需要增加 accountNo,或者定制 accountHisId 也作为分表字段
     * @param accountHisUpdateBatchDO
     * @return
     */
    int updateAccountStatusBatch(AccountHisUpdateBatchBO accountHisUpdateBatchDO);

    /**
     * 查询未入账的账户历史
     * account_status = 100 and version = 0 and amount >0
     * @param accountNo
     * @return
     */
    AccountHisDealBO selectDeal(@Param("accountNo") String accountNo);

    /**
     * 日汇总账户待结算金额
     * @param accountHisDailyCollectDO
     * @return
     */
    AccountHisDailyCollectBO dailyCollect(AccountHisDailyCollectDO accountHisDailyCollectDO);
    /**
     * 更新日汇总账户待结算历史记录
     * @param accountHisDailyCollectDO
     * @return
     */
    int updateDailyCollect(AccountHisDailyCollectDO accountHisDailyCollectDO);
}
