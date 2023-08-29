package com.tomato.seckill.domain.resp;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 秒杀活动查询
 *
 * @author lizhifu
 * @since 2023/3/22
 */
@Data
public class SeckillActivityQueryResp {

	/**
	 * 活动名称
	 */
	private String activityName;

	/**
	 * 活动描述
	 */
	private String activityDesc;

	/**
	 * 开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	private LocalDateTime endTime;

}
