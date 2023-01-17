package com.tomato.account.manager;

import com.tomato.util.StrUtil;
import com.tomato.util.date.DatePattern;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 账户编号生成
 * 21位 = 账户标识(2位) + 时间(8位) + 随机数(7位) +  商编后四位(4位)
 * @author lizhifu
 * @since 2023/1/1
 */
@Service
public class AccountNoManager {
    public String nextStringValue(String merchantNo){
        long aLong = ThreadLocalRandom.current().nextLong(1, 9999999);
        String date = LocalDate.now().format(DatePattern.PURE_DATE_FORMATTER);
        return "10" + date + StrUtil.fillAfter("0", Long.toString(aLong),7) + merchantNo.substring(merchantNo.length()-4);
    }

    public static void main(String[] args) {
        AccountNoManager accountNoManager = new AccountNoManager();
        System.out.println(accountNoManager.nextStringValue("1234"));
    }
}
