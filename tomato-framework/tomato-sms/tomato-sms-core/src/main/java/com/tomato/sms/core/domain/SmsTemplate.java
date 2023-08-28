package com.tomato.sms.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 短信发送模板
 *
 * @author lizhifu
 * @since 2023/8/28
 */
@Getter
@Setter
public class SmsTemplate {
    /**
     * 类型
     */
    private String type;

    /**
     * 参数列表
     */
    private Map<String, String> params;
}
