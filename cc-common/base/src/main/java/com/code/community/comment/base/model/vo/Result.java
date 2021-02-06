package com.code.community.comment.base.model.vo;

import com.code.community.comment.base.exception.ExceptionType;
import com.code.community.comment.base.exception.IExceptionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("通用返回类")
@Data
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final int  SUCCESSFUL_CODE = 200;
    private static final String SUCCESSFUL_MESSAGE = "SUCCESS";

    @ApiModelProperty("返回状态码")
    private long code;
    @ApiModelProperty("处理信息")
    private String message;
    @ApiModelProperty("返回数据")
    private T data;
    @ApiModelProperty("时间戳")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;


    protected Result(long code,String message,T data){
        this.code = code;
        this.message = message;
        this.data = data;
        this.time = LocalDateTime.now();
    }

    protected Result(IExceptionType exceptionType, T data) {
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMsg();
        this.data = data;
        this.time = LocalDateTime.now();
    }

    public static Result success(){
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESSFUL_CODE,SUCCESSFUL_MESSAGE,data);
    }

    /**
     * 默认为SYSTEM_EXCEPTION
     * @return
     */
    public static Result failed(){
        return failed(ExceptionType.SYSTEM_EXCEPTION, null);
    }

    public static  Result failed(IExceptionType exceptionType){
        return failed(exceptionType,null);
    }

    public static <T> Result failed(T data){
        return failed(ExceptionType.SYSTEM_EXCEPTION,data);
    }

    public static <T> Result<T> failed(IExceptionType exceptionType,T data) {
        return new Result<>(exceptionType,data);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESSFUL_CODE == this.code ;
    }

}
