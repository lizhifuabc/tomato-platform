package com.tomato.dynamic.thread.exception;


public class DynamicThreadException extends RuntimeException {

    public DynamicThreadException() {
        super();
    }

    public DynamicThreadException(String message) {
        super(message);
    }

    public DynamicThreadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicThreadException(Throwable cause) {
        super(cause);
    }
}
