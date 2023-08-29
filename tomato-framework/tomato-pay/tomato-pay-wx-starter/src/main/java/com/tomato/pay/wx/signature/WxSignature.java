package com.tomato.pay.wx.signature;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;

/**
 * 签名 加签 验签
 * <p>
 * 请求微信服务器时，需要根据我方的API证书对参数进行加签；微信服务器会根据我方签名验签以确定请求来自我方服务器；
 * <p>
 * 微信服务器响应请求并在响应报文中使用 [微信平台证书] 加签, 我方需要根据规则验签是否响应来自微信支付服务器
 * <p>
 * [微信平台证书]定期会进行更新，需要适当的时候获取最新的证书列表
 *
 * @author lizhifu
 * @since 2023/7/28
 */
@Slf4j
public class WxSignature {

	/**
	 * 随机字符串生成器
	 */
	private final IdGenerator nonceStrGenerator = new AlternativeJdkIdGenerator();

}
