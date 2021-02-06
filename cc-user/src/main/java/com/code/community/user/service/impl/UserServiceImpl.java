package com.code.community.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.community.comment.base.constant.NumberConstant;
import com.code.community.comment.base.constant.RedisConstant;
import com.code.community.comment.base.exception.CRUDException;
import com.code.community.comment.base.exception.CRUDExceptionType;
import com.code.community.comment.base.model.vo.Result;
import com.code.community.common.auth.UserContextHolder;
import com.code.community.common.exception.AuthException;
import com.code.community.common.factory.VoFactory;
import com.code.community.common.service.RedisService;
import com.code.community.user.dao.UserMapper;
import com.code.community.user.model.form.UserForm;
import com.code.community.user.model.po.User;
import com.code.community.user.model.vo.UserVo;
import com.code.community.user.provider.AuthProvider;
import com.code.community.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@CacheConfig(cacheNames = "user")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_ID_VALUE = "code-community";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String CLIENT_SECRET_VALUE = "123456";
    private static final String GRANT_TYPE = "grant_type";
    private static final String GRANT_TYPE_VALUE = "password";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthProvider authProvider;
    @Autowired
    private RedisService redisService;

    @Override
    public boolean register(User user) {
        String userName = user.getUserName();
        if(redisService.get(RedisConstant.USER_PREFIX + userName) !=null){
            log.info("存在相同用户名，注册失败");
            return false;
        }
        User one = this.lambdaQuery().eq(User::getUserName, userName).one();
        if (Objects.nonNull(one)) {
            log.info("存在相同用户名，注册失败");
            return false;
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        boolean save = this.save(user);
        if(save){
            redisService.set(RedisConstant.USER_PREFIX+user.getUserName(),user,RedisConstant.EXPIRE_TIME);
            return true;
        }
        throw new CRUDException(CRUDExceptionType.INSERT_FAILED,"用户记录插入失败");
    }

    @Override
    public UserVo getUserByToken() {
        User currentUser = UserContextHolder.getUser();
        User user = (User) redisService.get(RedisConstant.USER_PREFIX + currentUser.getUserName());
        if(Objects.nonNull(user)){
            return VoFactory.createVo(UserVo.class, user);
        }
        user = this.getById(currentUser.getId());
        if(Objects.isNull(user)){
            throw new CRUDException(CRUDExceptionType.NOT_FOUND,"用户未找到,id:" + currentUser);
        }
        redisService.set(RedisConstant.USER_PREFIX+user.getUserName(),user,RedisConstant.EXPIRE_TIME);
        return VoFactory.createVo(UserVo.class, user);
    }

    @Cacheable(key = "#userName")
    @Override
    public User getUserByUserName(String userName) {
        User user = this.lambdaQuery().eq(User::getUserName, userName).one();
        return user;
    }

    @Override
    public Result login(UserForm userForm) {
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put(CLIENT_ID, CLIENT_ID_VALUE);
        stringStringHashMap.put(CLIENT_SECRET, CLIENT_SECRET_VALUE);
        stringStringHashMap.put(GRANT_TYPE, GRANT_TYPE_VALUE);
        stringStringHashMap.put(USERNAME, userForm.getUserName());
        stringStringHashMap.put(PASSWORD, userForm.getPassword());
        Result result = authProvider.postAccessToken(stringStringHashMap);
        return result;
    }

    @Override
    public UserVo updateUserInfo(User user) {
        User currentUser = UserContextHolder.getUser();
        if(!currentUser.getId().equals(user.getId())){
            throw new AuthException();
        }
        boolean updateFlag = this.updateById(user);
        if (!updateFlag) {
            throw new CRUDException(CRUDExceptionType.UPDATE_FAILED,"用户信息更新失败,user：" + user);
        }
        //因为参数user的密码为空
        User byId = this.getById(user.getId());
        redisService.set(RedisConstant.USER_PREFIX+user.getUserName(),byId,RedisConstant.EXPIRE_TIME);
        return VoFactory.createVo(UserVo.class, user);
    }

    //TODO 只查询头像和用户名
    @Override
    public List<UserVo> batchGetUsersById(List<String> idList) {
        List<User> users = this.listByIds(idList);
        if (CollectionUtil.isEmpty(users)) {
            throw new CRUDException(CRUDExceptionType.NOT_FOUND,"头像查询失败" + idList);
        }
        List<UserVo> userVos = new ArrayList<>(NumberConstant.TEN);
        users.forEach(user -> userVos.add(VoFactory.createVo(UserVo.class, user)));
        return userVos;
    }

}
