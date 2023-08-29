package com.tomato.pay.common.api;

import com.tomato.pay.common.domain.CallBackReq;

/**
 * 支付服务
 *
 * @author lizhifu
 * @since 2023/7/27
 */
public interface PayService {

	/**
	 * 回调校验
	 * @param callBackReq 回调请求
	 * @return 是否校验成功
	 */
	boolean verify(CallBackReq callBackReq);

}
