package com.tomato.channel.vo.resp;

import lombok.Data;

/**
 * 渠道响应参数
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Data
public class ChannelScanResp {

	/**
	 * 扫码地址
	 */
	private String scanUrl;

	/**
	 * 渠道编号
	 */
	private String channelNo;

}
