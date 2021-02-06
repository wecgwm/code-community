package com.code.community.user.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.code.community.comment.base.model.vo.Result;
import com.code.community.common.auth.Auth;
import com.code.community.common.validator.group.Get;
import com.code.community.common.validator.group.Insert;
import com.code.community.common.validator.group.Update;
import com.code.community.user.model.form.UserForm;
import com.code.community.user.model.po.User;
import com.code.community.user.model.vo.UserVo;
import com.code.community.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@Api("用户相关接口")
public class UserController {
    /**
     * TODO 1.事务
     *      2.mq
     *      3.springboot admin
     *
     */
    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取user信息",notes = "根据UserContextHolder中上下文信息获取用户")
    @Auth
    @PostMapping("/token")
    public Result<UserVo> getUser(){
        return Result.success(userService.getUserByToken());
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result<UserVo> login(@RequestBody @Validated({Get.class}) UserForm userForm) {
        log.info("login:{}",userForm);
        return userService.login(userForm);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result register(@Validated({Insert.class}) @RequestBody UserForm userForm) {
        log.info("register:{}",userForm);
        boolean register = userService.register(userForm.toPo(User.class));
        return Result.success(register);
    }

    @ApiOperation(value = "更新用户信息")
    @Auth
    @PutMapping
    public Result updateUser(@RequestBody @Validated({Update.class}) UserForm userForm) {
        log.info("updateUser,userForm:{}......", userForm);
        User user = userForm.toPo(User.class);
        return Result.success(userService.updateUserInfo(user));
    }

    @ApiOperation(value = "oauth认证入口",notes = "获取完整用户信息,用于校验用户密码是否正确")
    @GetMapping("/{userName}")
    public Result<User> getUserByUserName(@PathVariable(value = "userName") String userName) {
        log.info("getUserByUserName,userName:{}",userName);
        User user = userService.getUserByUserName(userName);
        return Result.success(user);
    }

    @ApiOperation(value = "批量获取user信息",notes = "评论服务调用,获取评论用户头像和用户名")
    @PostMapping("/batchGetUserById")
    public Result<List<UserVo>> batchGetUsersById(@RequestBody List<String> idList) {
        log.info("batchGetUsersById,idList:{}", idList);
        if (CollectionUtil.isEmpty(idList)) {
            return Result.failed("参数idList为空");
        }
        List<UserVo> userVos = userService.batchGetUsersById(idList);
        return Result.success(userVos);
    }

}
