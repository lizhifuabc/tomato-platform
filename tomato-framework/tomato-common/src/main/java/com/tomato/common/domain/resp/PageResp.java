package com.tomato.common.domain.resp;

import com.tomato.common.constants.CommonRespCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 分页返回对象
 *
 * @author lizhifu
 * @since 2022/11/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageResp<T> extends Resp<T> {

	/**
	 * 当前页
	 */
	@Schema(description = "当前页")
	private Long pageNum;

	/**
	 * 每页的数量
	 */
	@Schema(description = "每页的数量")
	private Long pageSize;

	/**
	 * 总记录数
	 */
	@Schema(description = "总记录数")
	private Long total;

	/**
	 * 总页数
	 */
	@Schema(description = "总页数")
	private Long pages;

	public static <T> PageResp<T> of(T data, Long total, Long pageSize, Long pageNum) {
		PageResp<T> response = new PageResp<>();
		response.setSuccess(true);
		response.setCode(CommonRespCode.SUCCESS.code());
		response.setMsg(CommonRespCode.SUCCESS.msg());
		response.setData(data);
		response.setTotal(total);
		response.setPageSize(pageSize);
		response.setPageNum(pageNum);
		response.setPages(total % pageSize == 0 ? total / pageSize : (total / pageSize + 1));
		return response;
	}
}
