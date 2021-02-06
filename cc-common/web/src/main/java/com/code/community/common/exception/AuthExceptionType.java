package com.code.community.common.exception;

import com.code.community.comment.base.exception.IExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  AuthExceptionType implements IExceptionType {
    AUTH_FAILED(401,"认证失败");

    private long code;
    private String msg;

}
