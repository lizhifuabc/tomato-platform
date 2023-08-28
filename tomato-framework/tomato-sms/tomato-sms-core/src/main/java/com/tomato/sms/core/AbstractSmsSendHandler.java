package com.tomato.sms.core;

import com.tomato.sms.core.domain.SmsTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 短信发送处理
 *
 * @author lizhifu
 * @since 2023/8/28
 */
@Slf4j
public abstract class AbstractSmsSendHandler implements SmsSendHandler{
    /**
     * 执行发送
     * @param template 短信模版
     * @param phoneList 手机号
     * @return true 发送成功，false发送失败
     */
    protected abstract boolean exe(SmsTemplate template, List<String> phoneList);
    @Override
    public boolean send(SmsTemplate template, List<String> phoneList) {
        boolean exe = exe(template,phoneList);
        log.info("短信发送结果：{}",exe);
        return exe;
    }
}
