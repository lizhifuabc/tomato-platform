package com.tomato.common.exception;

import com.tomato.common.constants.RespCode;
import lombok.Getter;

import java.util.Optional;

/**
 * 抽象异常
 *
 * @author lizhifu
 * @since 2023/7/18
 */
@Getter
public class AbstractException extends RuntimeException {
    private final String code;
    private final String msg;
    public AbstractException(RespCode respCode, String message, Throwable throwable){
        super(message,throwable);
        this.code = respCode.code();
        this.msg = Optional.ofNullable(message).orElse(respCode.msg());
    }
}
