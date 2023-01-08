package com.tomato.account;


import com.tomato.account.enums.CycleTypeEnum;
import com.tomato.account.util.SettleDayUtil;

import java.time.LocalDate;

/**
 * 时间
 *
 * @author lizhifu
 * @since 2023/1/8
 */
public class TimeTest {
    public static void main(String[] args) {
        String[] cycleData = {"1","2","4"};
        LocalDate nextSettleDate = LocalDate.now().plusDays(1);
        System.out.println("当前时间:"+nextSettleDate);
//        System.out.println(SettleDayUtil.nextSettleDateByWeek(cycleData,nextSettleDate));
        System.out.println(SettleDayUtil.settleDate(cycleData,nextSettleDate, CycleTypeEnum.WEEK.getValue(), 3));
    }
}
