package com.code.community.comment.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举异常状态码
 */
@Getter
@AllArgsConstructor
public enum ExceptionType implements IExceptionType {
    SYSTEM_EXCEPTION(-1,"系统错误"),TIME_OUT_EXCEPTION(-2,"超时错误"),
    VALIDATE_FAILED(-3,"参数校验错误");


    private long code;
    private String msg;
}
