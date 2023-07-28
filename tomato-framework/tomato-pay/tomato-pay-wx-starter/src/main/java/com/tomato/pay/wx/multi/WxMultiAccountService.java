package com.tomato.pay.wx.multi;

import com.tomato.pay.wx.multi.domain.WxMultiAccount;

import java.util.Set;

/**
 * 微信支付配置,多个商户信息
 *
 * @author lizhifu
 * @since 2023/7/28
 */
@FunctionalInterface
public interface WxMultiAccountService {
    /**
     * 加载微信支付配置,多个商户信息
     * @return Set
     */
    Set<WxMultiAccount> load();
}
