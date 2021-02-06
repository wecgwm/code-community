package com.code.community.common.handler;

import com.code.community.comment.base.exception.BaseException;
import com.code.community.comment.base.exception.CRUDException;
import com.code.community.comment.base.exception.ExceptionType;
import com.code.community.comment.base.model.vo.Result;
import com.code.community.common.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

/**
 * 全局异常处理,基类注入
 */
@Slf4j
//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BaseExceptionHandlerAdvice {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result handleValidException(MethodArgumentNotValidException ex) {
        log.error("validate exception,message:{}", ex.getMessage());
        //是否需要非空校验
        return Result.failed(ExceptionType.VALIDATE_FAILED, ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public Result handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (Objects.nonNull(fieldError)) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        log.error("validate exception,message:{}", message);
        return Result.failed(message);
    }

    @ResponseBody
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result authException(AuthException e){
        log.error("authException,code:{},msg:{},message:{}",e.getExceptionType().getCode(),
                e.getExceptionType().getMsg(),e.getMessage());
        return Result.failed(e.getExceptionType(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = CRUDException.class)
    public Result crudException(CRUDException crudException) {
        log.error("crudException,code:{},msg:{},message:{}", crudException.getExceptionType().getCode(),
                crudException.getExceptionType().getMsg(), crudException.getMessage());
        return Result.failed(crudException.getExceptionType(), crudException.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    public Result baseException(BaseException baseException) {
        log.error("baseException:code:{},msg{},message{}",
                baseException.getExceptionType().getCode(),
                baseException.getExceptionType().getMsg(), baseException.getMessage());
        return Result.failed(baseException.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public Result runtimeException(RuntimeException e) {
        log.error("runtimeException,message:{}",e.getMessage());
        e.printStackTrace();
        return Result.failed("RuntimeException!");
    }

    @ExceptionHandler(value = {Exception.class})
    public Result exception(Exception e) {
        log.error("Exception,message:{}", e.getMessage());
        e.printStackTrace();
        return Result.failed("Exception!");
    }

    @ExceptionHandler(value = {Throwable.class})
    public Result throwable(Throwable throwable) {
        log.error("throwable,message:{}", throwable.getMessage());
        throwable.printStackTrace();
        return Result.failed("Throwable!");
    }

}
