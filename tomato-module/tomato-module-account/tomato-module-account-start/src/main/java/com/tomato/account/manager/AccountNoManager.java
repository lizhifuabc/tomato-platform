package com.tomato.account.manager;

import com.google.common.base.Strings;
import com.tomato.utils.base.date.DatePattern;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 账户编号生成 21位 = 账户标识(2位) + 时间(8位) + 随机数(7位) + 商编后四位(4位)
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@Service
public class AccountNoManager {

	private static final String PREFIX = "10";

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

	public String nextStringValue(String merchantNo) {
		return buildResultString(merchantNo);
	}

	private static String buildResultString(String merchantNo) {
		long aLong = ThreadLocalRandom.current().nextLong(1, 9999999);
		String date = LocalDate.now().format(DATE_FORMATTER);
		String paddedLong = Strings.padStart(Long.toString(aLong), 7, '0');
		return PREFIX + date + paddedLong + merchantNo.substring(merchantNo.length() - 4);
	}

	public static void main(String[] args) {
		String merchantNo = "123456789012345";
		long aLong = ThreadLocalRandom.current().nextLong(1, 9999999);
		String date = LocalDate.now().format(DatePattern.PURE_DATE_FORMATTER);

		String res2 = "10" + date + Strings.padStart(Long.toString(aLong), 7, '0')
				+ merchantNo.substring(merchantNo.length() - 4);
		System.out.println(res2);

		String res3 = buildResultString(merchantNo);
		System.out.println(res3);
	}

}
