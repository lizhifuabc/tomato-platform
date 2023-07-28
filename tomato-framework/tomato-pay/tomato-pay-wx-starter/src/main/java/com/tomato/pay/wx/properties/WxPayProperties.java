package com.tomato.pay.wx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付配置
 *
 * @author lizhifu
 * @since 2023/7/28
 */
@Data
@ConfigurationProperties("wx.pay")
public class WxPayProperties {
    /**
     * 微信支付V3配置
     */
    private final Map<String, V3> v3 = new HashMap<>();
    @Data
    public static class V3 {
        /**
         * 微信支付appid，必填
         */
        private String appId;
        /**
         * 微信支付appSecret，必填
         */
        private String appSecret;
        /**
         * 微信支付appV3Secret，必填
         */
        private String appV3Secret;
        /**
         * 微信支付mchId，必填
         */
        private String mchId;
        /**
         * 微信支付partnerKey，选填
         */
        private String partnerKey;
        /**
         * 微信支付证书路径
         */
        private String certPath;
        /**
         * 微信支付证书绝对路径
         */
        private String certAbsolutePath;
        /**
         * 微信支付回调域名
         */
        private String domain;
    }
}
