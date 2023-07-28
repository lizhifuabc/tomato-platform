package com.tomato.pay.wx.multi.domain;

import com.tomato.pay.wx.properties.WxPayProperties;
import lombok.Data;

import java.security.KeyPair;

/**
 * 微信支付多账户
 *
 * @author lizhifu
 * @since 2023/7/28
 */
@Data
public class WxMultiAccount {
    /**
     * 密钥对
     */
    private KeyPair keyPair;
    /**
     * 证书序列号
     */
    private String serialNumber;
    /**
     * 账号标识
     */
    private String multiAccountId;
    /**
     * V3 配置
     */
    private WxPayProperties.V3 v3;
}
