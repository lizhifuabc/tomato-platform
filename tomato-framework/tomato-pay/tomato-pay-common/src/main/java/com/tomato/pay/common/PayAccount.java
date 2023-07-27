package com.tomato.pay.common;

import com.tomato.pay.common.enums.PayType;
import lombok.Data;

/**
 * 支付账户
 *
 * @author lizhifu
 * @since 2023/7/27
 */
@Data
public class PayAccount {
    /**
     * 支付账号唯一标识
     */
    private Integer id;
    /**
     *  支付合作id,商户id
     */
    private String partner;
    /**
     * 应用id
     */
    private String appId;
    /**
     * 支付平台公钥(签名校验使用)，
     * sign_type只有单一key时public_key与private_key相等，比如sign_type=MD5的情况
     */
    private String publicKey;
    /**
     * 应用私钥(生成签名)
     */
    private String privateKey;
    /**
     * 异步回调地址
     */
    private String notifyUrl;
    /**
     * 同步回调地址
     */
    private String returnUrl;
    /**
     * 收款账号
     */
    private String seller;
    /**
     * 请求证书地址，请使用绝对路径
     */
    private String keystorePath;
    /**
     * 证书对应的密码
     */
    private String storePassword;
    /**
     * 签名类型
     */
    private String signType;
    /**
     *  编码类型 枚举值，字符编码 utf-8,gbk等等
     */
    private String inputCharset;
    /**
     * 支付方式,aliPay：支付宝，wxPay：微信
     */
    private PayType payType;
    /**
     * 是否为测试环境
     */
    private boolean isTest;
}
