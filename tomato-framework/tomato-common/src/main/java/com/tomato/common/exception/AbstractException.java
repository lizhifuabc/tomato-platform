package com.tomato.common.exception;

import com.tomato.common.constants.RespCode;
import lombok.Getter;

import java.util.Optional;

/**
 * 抽象异常 TODO
 * <p> ClientException（客户端异常）
 * <p> BusinessException（业务逻辑异常）
 * <p> RemoteException（第三方服务异常
 *
 * @author lizhifu
 * @since 2023/7/18
 */
@Getter
public class AbstractException extends RuntimeException {
    private final String code;
    private final String msg;
    public AbstractException(RespCode respCode, String message, Throwable throwable){
        super(Optional.ofNullable(message).orElse(respCode.msg()),throwable);
        this.code = respCode.code();
        this.msg = Optional.ofNullable(message).orElse(respCode.msg());
    }
}
