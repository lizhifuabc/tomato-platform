package com.tomato.merchant.domain.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商户配置查询
 *
 * @author lizhifu
 * @since 2023/8/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantConfigQueryResp {

	/**
	 * 商户秘钥
	 */
	private String merchantKey;

}
