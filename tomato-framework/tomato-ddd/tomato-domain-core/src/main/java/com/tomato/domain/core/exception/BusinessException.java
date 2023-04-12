package com.tomato.domain.core.exception;

import com.tomato.domain.core.constants.RespCode;

/**
 * 业务逻辑异常,全局异常拦截后统一返回ResponseCodeConst.SYSTEM_ERROR
 *
 * @author lizhifu
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
    }

    public BusinessException(RespCode respCode) {
        super(respCode.msg());
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
