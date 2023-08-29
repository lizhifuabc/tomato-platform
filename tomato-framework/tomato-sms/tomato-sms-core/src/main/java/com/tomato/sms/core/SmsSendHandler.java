package com.tomato.sms.core;

import com.tomato.sms.core.domain.SmsTemplate;

import java.util.List;

/**
 * 短信发送处理
 *
 * @author lizhifu
 * @since 2023/8/28
 */
public interface SmsSendHandler {

	/**
	 * 发送短信
	 * @param template 短信模版填充相关内容
	 * @param phoneList 手机号码
	 * @return true 发送成功，false发送失败
	 */
	boolean send(SmsTemplate template, List<String> phoneList);

}
