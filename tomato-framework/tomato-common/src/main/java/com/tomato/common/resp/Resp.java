package com.tomato.common.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tomato.common.constants.CommonRespCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 返回数据结构
 *
 * @author lizhifu
 * @since 2022/11/21
 */
@Data
@NoArgsConstructor
@Schema(title = "统一响应返回实体", description = "所有Rest接口统一返回的实体定义")
public class Resp<T> {

	@Schema(title = "返回数据")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	@Schema(title = "是否成功")
	private boolean success;

	@Schema(title = "自定义响应编码")
	private String code;

	@Schema(title = "返回信息")
	private String msg;

	@Schema(title = "http状态码")
	private int status;

	/**
	 * 链路追踪TraceId
	 */
	@Schema(title = "链路追踪TraceId")
	private String traceId;

	@Schema(title = "响应时间戳")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private LocalDateTime timestamp = LocalDateTime.now();

	/**
	 * 成功
	 * @return Result
	 */
	public static Resp<Void> buildSuccess() {
		Resp<Void> resp = new Resp<>();
		resp.setSuccess(true);
		resp.setCode(CommonRespCode.SUCCESS.code());
		resp.setMsg(CommonRespCode.SUCCESS.msg());
		resp.setStatus(CommonRespCode.SC_OK);
		return resp;
	}

	/**
	 * 成功
	 * @return Result
	 */
	public static Resp<Void> buildSuccess(String msg) {
		Resp<Void> resp = new Resp<>();
		resp.setSuccess(true);
		resp.setCode(CommonRespCode.SUCCESS.code());
		resp.setStatus(CommonRespCode.SC_OK);
		resp.setMsg(msg);
		return resp;
	}

	public static <T> Resp<T> of(T data) {
		Resp<T> resp = new Resp<>();
		resp.setSuccess(true);
		resp.setCode(CommonRespCode.SUCCESS.code());
		resp.setMsg(CommonRespCode.SUCCESS.msg());
		resp.setStatus(CommonRespCode.SC_OK);
		resp.setData(data);
		return resp;
	}

	/**
	 * 失败
	 * @param code 错误码
	 * @param msg 错误信息
	 * @return Result
	 */
	public static <T> Resp<T> buildFailure(String code, String msg) {
		Resp<T> resp = new Resp<>();
		resp.setSuccess(false);
		resp.setCode(code);
		resp.setMsg(msg);
		resp.setStatus(CommonRespCode.SC_INTERNAL_SERVER_ERROR);
		return resp;
	}

	/**
	 * 失败
	 * @param msg 错误信息
	 * @return Result
	 */
	public static <T> Resp<T> buildFailure(String msg) {
		Resp<T> resp = new Resp<>();
		resp.setSuccess(false);
		resp.setCode(CommonRespCode.INTERNAL_SERVER_ERROR.code());
		resp.setStatus(CommonRespCode.SC_INTERNAL_SERVER_ERROR);
		resp.setMsg(msg);
		return resp;
	}

}
