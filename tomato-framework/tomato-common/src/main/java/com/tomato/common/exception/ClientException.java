package com.tomato.common.exception;

import com.tomato.common.constants.CommonRespCode;
import com.tomato.common.resp.RespCode;

/**
 * 客户端异常
 *
 * @author lizhifu
 * @since 2023/7/18
 */
public class ClientException extends AbstractException {
    public ClientException() {
        this(CommonRespCode.INTERNAL_SERVER_ERROR,null,null);
    }

    public ClientException(RespCode respCode) {
        this(respCode,null,null);
    }

    public ClientException(String message) {
        this(CommonRespCode.INTERNAL_SERVER_ERROR,message,null);
    }

    public ClientException(String message, Throwable cause) {
        this(CommonRespCode.INTERNAL_SERVER_ERROR,message,cause);
    }

    public ClientException(Throwable cause) {
        this(CommonRespCode.INTERNAL_SERVER_ERROR,null,cause);
    }
    public ClientException(RespCode respCode, String message, Throwable throwable) {
        super(respCode, message, throwable);
    }
}
