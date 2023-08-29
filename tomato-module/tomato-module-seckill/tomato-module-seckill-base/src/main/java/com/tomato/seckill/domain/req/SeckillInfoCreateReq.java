package com.tomato.seckill.domain.req;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * 秒杀活动记录创建
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Data
public class SeckillInfoCreateReq {

	/**
	 * 活动名称
	 */
	@NotNull(message = "活动名称不能为空")
	@Length(max = 64, message = "活动名称最多64字符")
	private String activityName;

	/**
	 * 活动描述
	 */
	@NotNull(message = "活动描述不能为空")
	@Length(max = 128, message = "活动描述最多128字符")
	private String activityDesc;

	/**
	 * 开始时间
	 */
	@Future
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@Future
	private LocalDateTime endTime;

}
