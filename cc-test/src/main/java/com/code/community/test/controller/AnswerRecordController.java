package com.code.community.test.controller;

import com.code.community.comment.base.model.vo.Result;
import com.code.community.common.auth.Auth;
import com.code.community.common.auth.UserContextHolder;
import com.code.community.common.model.PageInfo;
import com.code.community.test.service.AnswerRecordService;
import com.code.community.user.model.po.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answerRecord")
@Slf4j
@Api("做题记录相关接口")
public class AnswerRecordController {

    private static final String USER = "user";

    @Autowired
    AnswerRecordService answerRecordService;

    @Auth
    @GetMapping
    @ApiOperation(value = "获取用户做题记录")
    public Result getQuestionRecord(@Validated PageInfo pageInfo){
        User user = UserContextHolder.getUser();
        log.info("getAnswerRecord,pageInfo{}",user,pageInfo);
        return Result.success(answerRecordService.getQuestionRecord(pageInfo,user));
    }

}
