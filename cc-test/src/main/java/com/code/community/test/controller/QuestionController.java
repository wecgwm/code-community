package com.code.community.test.controller;

import com.code.community.comment.base.model.vo.Result;
import com.code.community.test.model.vo.QuestionVo;
import com.code.community.test.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@Slf4j
@Api("题目相关接口")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/get-category")
    @ApiOperation(value = "获取问题类别")
    public Result getCategory(){
        log.info("getCategory");
        return Result.success(questionService.getCategory());
    }

    @GetMapping("/{category}")
    @ApiOperation(value = "根据类别获取问题")
    public Result getQuestion(@PathVariable("category") String category){
        log.info("getQuestionByCategory:{}",category);
        return Result.success(questionService.getQuestionByCategory(category));
    }

    //TODO
    @PostMapping
    @ApiOperation(value = "添加问题与答案")
    public Result addQuestion(@RequestBody QuestionVo questionVo ){
        log.info("addQuestion{}",questionVo);
        return Result.success(questionService.addQuestion(questionVo));
    }

}
