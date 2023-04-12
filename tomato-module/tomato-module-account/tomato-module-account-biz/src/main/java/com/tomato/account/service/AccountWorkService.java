package com.tomato.account.service;

import com.tomato.account.dao.AccountWorkDao;
import com.tomato.account.domain.entity.AccountWorkEntity;
import com.tomato.account.enums.CycleTypeEnum;
import com.tomato.account.enums.DayTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 节假日控制
 *
 * @author lizhifu
 * @since 2023/1/10
 */
@Service
@Slf4j
public class AccountWorkService implements InitializingBean {
    private static final ConcurrentHashMap<LocalDate, AccountWorkEntity> map = new ConcurrentHashMap();
    private final AccountWorkDao accountWorkDao;

    public AccountWorkService(AccountWorkDao accountWorkDao) {
        this.accountWorkDao = accountWorkDao;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("初始化节假日控制 start:{}",LocalDate.now());
        List<AccountWorkEntity> accountWorkEntities = accountWorkDao.selectByWorkDay(LocalDate.now());
        log.info("初始化节假日控制 end:{}",accountWorkEntities);
        accountWorkEntities.forEach(accountWorkEntity -> {
            map.putIfAbsent(accountWorkEntity.getWorkDay(),accountWorkEntity);
        });
    }
    public LocalDate nextWorkDay(LocalDate localDate,CycleTypeEnum cycleTypeEnum){
        // TODO 增加循环控制
        while (isHoliday(localDate,cycleTypeEnum)) {
            localDate = localDate.plusDays(1);
        }
        return localDate;
    }
    /**
     * 是否节假日,如果是，+1 天
     * @param localDate 日期
     * @return
     */
    public boolean isHoliday(LocalDate localDate, CycleTypeEnum cycleTypeEnum){
        if(map.containsKey(localDate)){
            AccountWorkEntity accountWorkEntity = map.get(localDate);
            DayTypeEnum dayTypeEnum = DayTypeEnum.fromValue(accountWorkEntity.getDayType());
            return switch (dayTypeEnum) {
                case WORK_DAY, REST_WORK_DAY -> false;
                case WEEK_REST_DAY, LEGAL_REST_DAY -> true;
            };
        }
        // 周末是否结算
        return switch (cycleTypeEnum){
            case MONTH, WEEK -> false;
            case MONTH_WORK,WEEK_WORK ->  (localDate.getDayOfWeek().getValue() >= 6);
        };
    }
}
