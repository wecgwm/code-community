package com.code.community.comment.base.exception;

public class CRUDException extends BaseException{
    public CRUDException(CRUDExceptionType crudExceptionType) {
        super(crudExceptionType);
    }

    public CRUDException(CRUDExceptionType crudExceptionType,String message) {
        super(crudExceptionType,message);
    }
}
