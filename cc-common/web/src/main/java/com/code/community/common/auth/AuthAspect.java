package com.code.community.common.auth;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.code.community.common.exception.AuthException;
import com.code.community.user.model.po.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;


@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Pointcut("@annotation(com.code.community.common.auth.Auth)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attribute.getRequest();
        String userStr = request.getHeader("user");
        if(StrUtil.isEmpty(userStr)){
            throw new AuthException();
        }
        User user = JSONUtil.toBean(userStr, User.class);
        if(Objects.isNull(user) || StrUtil.isEmpty(user.getId())){
            throw new AuthException();
        }
        HashMap<String, User> userContext = new HashMap<>();
        userContext.put("user",user);
        UserContextHolder.setContext(userContext);
    }

    @After("pointcut()")
    public void doAfter(JoinPoint joinPoint){
        UserContextHolder.clear();
    }

}
