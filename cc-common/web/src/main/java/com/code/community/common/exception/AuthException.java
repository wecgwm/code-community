package com.code.community.common.exception;

import com.code.community.comment.base.exception.BaseException;

public class AuthException extends BaseException {
    public AuthException(){
        super(AuthExceptionType.AUTH_FAILED,"认证失败，请重新登录");
    }

    public AuthException(String message) {
        super(AuthExceptionType.AUTH_FAILED,message);
    }
}
