package com.code.community.auth.service;

import com.code.community.auth.dto.CustomUserDetails;
import com.code.community.auth.provider.UserProvider;
import com.code.community.comment.base.model.vo.Result;
import com.code.community.user.model.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义UserDetailsService
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserProvider userProvider;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Result<User> userByUserName = userProvider.getUserByUserName(userName);
        User user = (User)userByUserName.getData();
        return new CustomUserDetails(user);
    }

}
