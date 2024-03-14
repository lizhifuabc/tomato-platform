package com.tomato.common.domain.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 分页查询请求
 *
 * @author lizhifu
 * @since 2022/11/21
 */
@Data
public class PageQueryReq {

	@Schema(description = "页码(不能为空)", example = "1")
	@NotNull(message = "分页参数不能为空")
	private Integer pageNum;

	@Schema(description = "每页数量(不能为空)", example = "10")
	@NotNull(message = "每页数量不能为空")
	@Max(value = 200, message = "每页最大为200")
	private Integer pageSize;

	@Schema(description = "是否查询总条数")
	protected Boolean searchCount;

	@Schema(description = "排序字段集合")
	@Size(max = 10, message = "排序字段最多10")
	@Valid
	private List<SortItem> sortItemList;
	/**
	 * 排序DTO类
	 */
	@Data
	public static class SortItem {

		@Schema(description = "true正序|false倒序")
		@NotNull(message = "排序规则不能为空")
		private Boolean isAsc;

		@Schema(description = "排序字段")
		@NotBlank(message = "排序字段不能为空")
		@Length(max = 30, message = "排序字段最多30")
		private String column;
	}
}
