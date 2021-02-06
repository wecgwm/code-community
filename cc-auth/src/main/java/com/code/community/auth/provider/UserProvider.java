package com.code.community.auth.provider;

import com.code.community.comment.base.model.vo.Result;
import com.code.community.user.model.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user")
public interface UserProvider {

    @GetMapping("/user/{userName}")
    Result<User> getUserByUserName(@PathVariable("userName") String userName);

}
