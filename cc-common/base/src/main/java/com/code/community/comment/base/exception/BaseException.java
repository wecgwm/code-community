package com.code.community.comment.base.exception;

import lombok.Getter;


@Getter
public class BaseException extends RuntimeException {

    private final IExceptionType exceptionType;

    /**
     * 默认为SYSTEM_EXCEPTION
     */
    public BaseException() {
        this(ExceptionType.SYSTEM_EXCEPTION);
    }

    public BaseException(String message) {
        this(ExceptionType.SYSTEM_EXCEPTION, message);
    }

    public BaseException(IExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public BaseException(IExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }

}
