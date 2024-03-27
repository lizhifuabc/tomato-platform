package com.tomato.business.pay;

import lombok.Data;

/**
 * 公共参数
 *
 * @author lizhifu
 * @since 2024/3/13
 */
@Data
public abstract class AbstractPayCommon {
	/**
	 * 签名
	 */
	private String sign;
}
