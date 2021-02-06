package com.code.community.user.provider;

import com.code.community.comment.base.model.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("auth")
public interface AuthProvider {
    @PostMapping("/oauth/token")
    Result postAccessToken(@RequestParam Map<String, String> parameters);
}
