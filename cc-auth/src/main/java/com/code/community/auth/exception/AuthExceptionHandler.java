package com.code.community.auth.exception;

import com.code.community.comment.base.model.vo.Result;
import com.code.community.common.handler.BaseExceptionHandlerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler extends BaseExceptionHandlerAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    public Result usernameNotFoundException(UsernameNotFoundException e){
      log.error("UsernameNotFoundException,message:{}",e.getMessage());
      return Result.failed("用户名或密码错误");
    }

    @ExceptionHandler(InvalidGrantException.class)
    public Result invalidGrantExceptionException(InvalidGrantException e){
        log.error("InvalidGrantException,message:{}",e.getMessage());
        return Result.failed("用户名或密码错误");
    }

}
