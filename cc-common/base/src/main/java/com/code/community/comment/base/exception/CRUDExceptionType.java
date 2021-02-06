package com.code.community.comment.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CRUDExceptionType implements IExceptionType{
    NOT_FOUND(3001,"未找到"),INSERT_FAILED(3002,"插入失败"),
    UPDATE_FAILED(3003,"更新失败"),DELETE_FAILED(3004,"删除失败");

    private long code;
    private String msg;
}
