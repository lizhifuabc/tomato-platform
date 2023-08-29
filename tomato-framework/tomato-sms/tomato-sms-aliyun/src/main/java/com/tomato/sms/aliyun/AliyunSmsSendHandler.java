package com.tomato.sms.aliyun;

import com.tomato.sms.core.AbstractSmsSendHandler;
import com.tomato.sms.core.domain.SmsTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 阿里云短信
 *
 * @author lizhifu
 * @since 2023/8/28
 */
@Slf4j
public class AliyunSmsSendHandler extends AbstractSmsSendHandler {

	@Override
	protected boolean exe(SmsTemplate template, List<String> phoneList) {
		return false;
	}

}
