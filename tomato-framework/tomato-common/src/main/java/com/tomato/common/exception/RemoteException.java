package com.tomato.common.exception;

import com.tomato.common.constants.CommonRespCode;
import com.tomato.common.constants.RespCode;

/**
 * 第三方服务异常
 *
 * @author lizhifu
 * @since 2023/7/18
 */
public class RemoteException extends AbstractException{
    public RemoteException() {
        this(CommonRespCode.INTERNAL_SERVER_ERROR,null,null);
    }

    public RemoteException(RespCode respCode) {
        this(respCode,null,null);
    }

    public RemoteException(String message) {
        this(CommonRespCode.INTERNAL_SERVER_ERROR,message,null);
    }

    public RemoteException(String message, Throwable cause) {
        this(CommonRespCode.INTERNAL_SERVER_ERROR,message,cause);
    }

    public RemoteException(Throwable cause) {
        this(CommonRespCode.INTERNAL_SERVER_ERROR,null,cause);
    }
    public RemoteException(RespCode respCode, String message, Throwable throwable) {
        super(respCode, message, throwable);
    }
}
