package com.code.community.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.code.community.comment.base.model.vo.Result;
import com.code.community.user.model.form.UserForm;
import com.code.community.user.model.po.User;
import com.code.community.user.model.vo.UserVo;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * 用户登录
     * @param userForm
     * @return
     */
    Result login(UserForm userForm);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    UserVo updateUserInfo(User user);

    /**
     * 批量获取用户信息
     * @param idList
     * @return
     */
    List<UserVo> batchGetUsersById(List<String> idList);

    /**
     * 获取完整用户信息(包含密码)
     * @param userName
     * @return
     */
    User getUserByUserName(String userName);

    /**
     * 获取用户信息
     * @return
     */
    UserVo getUserByToken();
}
