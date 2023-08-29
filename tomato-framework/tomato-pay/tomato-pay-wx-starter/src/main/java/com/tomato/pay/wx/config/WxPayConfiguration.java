package com.tomato.pay.wx.config;

import com.tomato.pay.wx.multi.DefaultWxMultiAccountService;
import com.tomato.pay.wx.multi.WxMultiAccountService;
import com.tomato.pay.wx.properties.WxPayProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;

/**
 * 微信支付配置
 *
 * @author lizhifu
 * @since 2023/7/28
 */
@AutoConfiguration
@Import({ WxPayProperties.class })
public class WxPayConfiguration {

	/**
	 * 微信支付多账户配置
	 * @param wxPayProperties 配置信息
	 * @param resourceLoader 资源加载器
	 * @return WxMultiAccountService 多账户配置
	 */
	@Bean
	@ConditionalOnMissingBean
	public WxMultiAccountService wxMultiAccountService(WxPayProperties wxPayProperties, ResourceLoader resourceLoader) {
		return new DefaultWxMultiAccountService(wxPayProperties, resourceLoader);
	}

}