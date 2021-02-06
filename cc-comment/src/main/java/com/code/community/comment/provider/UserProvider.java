package com.code.community.comment.provider;

import com.code.community.user.model.vo.UserVo;
import com.code.community.comment.base.model.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("user")
public interface UserProvider {
    @PostMapping("/user/batchGetUserById")
    Result<List<UserVo>> batchGetUsersById(@RequestBody List<String> idList);
}
