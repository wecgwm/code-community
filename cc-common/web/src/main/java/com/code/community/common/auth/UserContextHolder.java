package com.code.community.common.auth;

import com.code.community.user.model.po.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 用户信息上下文存储
 */
public class UserContextHolder {

    private static final String USER = "user";
    private static final ThreadLocal<Map<String, User>> threadLocal = new ThreadLocal<>();

    /**
     * 设置上下文信息
     * @param context
     */
    public static void setContext(Map<String,User> context){
        threadLocal.set(context);
    }

    /**
     * 获取上下文信息
     * @return
     */
    public static Map<String,User> getContext(){
        return threadLocal.get();
    }

    /**
     * 获取用户
     * @return
     */
    public static User getUser(){
        return Optional.ofNullable(threadLocal.get()).orElse(new HashMap<>()).get(USER);
    }


    /**
     * 线程池重用线程时，不会清空Thread Local,所以要手动remove避免内存泄露
     */
    public static void clear(){
        threadLocal.remove();
    }
}
