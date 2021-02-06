package com.code.community.test.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.code.community.comment.base.constant.NumberConstant;
import com.code.community.comment.base.model.vo.Result;
import com.code.community.common.auth.Auth;
import com.code.community.test.model.vo.AnswerVo;
import com.code.community.test.service.AnswerRecordService;
import com.code.community.test.service.AnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/answer")
@Slf4j
@Api("答案相关接口")
public class AnswerController {
    private static final String USER = "user";

    @Autowired
    AnswerService answerService;
    @Autowired
    AnswerRecordService answerRecordService;

    @Auth
    @PostMapping("/score")
    @ApiOperation(value = "获取成绩并保存用户做题记录")
    public Result getScoreAndSaveRecord(@RequestBody List<AnswerVo> answerVos){
        log.info("getScore:{}",answerVos);
        if(CollectionUtil.isEmpty(answerVos)) {
            return Result.success(NumberConstant.ZERO);
        }
        int scoreByAnswers = answerService.getScoreByAnswers(answerVos);
        answerRecordService.saveAnswerRecord(answerVos);
        return Result.success(scoreByAnswers);
    }

    @PostMapping
    @ApiOperation(value = "获取正确答案")
    public Result getCorrectAnswers(@RequestBody List<String> questionIds){
        log.info("getCorrectAnswers,questionIds:{}",questionIds);
        return Result.success(answerService.getCorrectAnswersByQuestionIds(questionIds));
    }

}
