package com.tomato.account.util;

import com.tomato.account.enums.CycleTypeEnum;
import com.tomato.domain.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;

/**
 * 结算日期计算
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Slf4j
public class SettleDayUtil {
    public static void main(String[] args) {
        // 下一结算日计算，周期类型：WEEK, 结算日设置：[1, 2, 3, 4, 6, 7], 当前值：2023-01-08
        String[] cycleData = {"1","2","3","4","6","7"};
        System.out.println(nextSettleDateByWeek(cycleData,LocalDate.now()) );
    }
    public static LocalDate settleDate(String[] cycleData, LocalDate nextSettle,CycleTypeEnum cycleTypeEnum,int reserveDay){
        LocalDate nextSettleDate = nextSettleDate(cycleData,nextSettle,cycleTypeEnum);
        // 风险时间 = 下次结算时间 - （风险预存期 + 1）
        // 防止长久未结算的情况
        // 当前时间：2023年01月08日
        // 下次结算时间：2023年01月20日
        // 风险时间 = 2023年01月20日 - （1 + 1）= 2023年01月18日
        // 当前时间 > 风险时间
        // TODO 增加循环控制
        while (LocalDate.now().isAfter(nextSettleDate.minusDays(reserveDay))){
            nextSettleDate = nextSettleDate(cycleData,nextSettleDate,cycleTypeEnum);
        }
        return nextSettleDate;
    }
    /**
     * 下一结算日计算
     * @param cycleData 结算日设置
     * @param nextSettle 当前值
     * @param cycleTypeEnum 周期类型
     * @return 下一结算日
     */
    private static LocalDate nextSettleDate(String[] cycleData, LocalDate nextSettle,CycleTypeEnum cycleTypeEnum){
        log.info("下一结算日计算，周期类型：{}, 结算日设置：{}, 当前值：{}",cycleTypeEnum,cycleData,nextSettle);
        return switch (cycleTypeEnum) {
            case MONTH, MONTH_WORK -> nextSettleDateByMonth(cycleData, nextSettle);
            case WEEK, WEEK_WORK -> nextSettleDateByWeek(cycleData, nextSettle);
            default -> throw new BusinessException("不支持的结算周期类型！");
        };
    }
    private static LocalDate nextSettleDateByWeek(String[] cycleData, LocalDate nextSettle){
        // 星期几
        int week = nextSettle.getDayOfWeek().getValue();
        // 最近的日期
        String d = Arrays.stream(cycleData).filter(data -> Integer.parseInt(data) > week).findFirst().orElse(cycleData[0]);
        int between = Integer.parseInt(d) - week;

        if(between <= 0){
            // 跨周
            nextSettle = nextSettle.plusDays(Integer.parseInt(d));
        }else {
            // 同一周
            nextSettle = nextSettle.plusDays(between);
        }
        return nextSettle;
    }
    private static LocalDate nextSettleDateByMonth(String[] cycleData,LocalDate nextSettle){
        // 月几号
        int month = nextSettle.getDayOfMonth();
        // 最近的日期
        String d = Arrays.stream(cycleData).filter(data -> Integer.parseInt(data) > month).findFirst().orElse(cycleData[0]);
        int between = Integer.parseInt(d) - month;

        if(between <= 0){
            // 跨月
            LocalDate nextMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1);
            // 2月 && 最近的日期
            if(nextMonth.getMonth().getValue() == 2 && Integer.parseInt(d) >= nextMonth.lengthOfMonth()){
                return nextMonth.with(TemporalAdjusters.lastDayOfMonth());
            }
            return nextMonth.plusDays(Integer.parseInt(d) - 1);
        }else {
            // 同一月
            return nextSettle.plusDays(between);
        }
    }
}
