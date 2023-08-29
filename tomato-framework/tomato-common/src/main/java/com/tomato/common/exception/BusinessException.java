package com.tomato.common.exception;

import com.tomato.common.constants.CommonRespCode;
import com.tomato.common.resp.RespCode;

/**
 * 业务逻辑异常,全局异常拦截后统一返回ResponseCodeConst.SYSTEM_ERROR
 *
 * @author lizhifu
 */
public class BusinessException extends AbstractException {

	public BusinessException() {
		this(CommonRespCode.INTERNAL_SERVER_ERROR, null, null);
	}

	public BusinessException(RespCode respCode) {
		this(respCode, null, null);
	}

	public BusinessException(String message) {
		this(CommonRespCode.INTERNAL_SERVER_ERROR, message, null);
	}

	public BusinessException(String message, Throwable cause) {
		this(CommonRespCode.INTERNAL_SERVER_ERROR, message, cause);
	}

	public BusinessException(Throwable cause) {
		this(CommonRespCode.INTERNAL_SERVER_ERROR, null, cause);
	}

	public BusinessException(RespCode respCode, String message, Throwable throwable) {
		super(respCode, message, throwable);
	}

}
