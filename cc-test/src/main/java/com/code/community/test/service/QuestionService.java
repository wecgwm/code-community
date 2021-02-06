package com.code.community.test.service;

import com.code.community.test.model.po.Answer;
import com.code.community.test.model.vo.QuestionVo;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    /**
     * 获取题目类别
     * @return
     */
    Set<String> getCategory();

    /**
     * 获取题目
     * @param category 题目类别
     * @return
     */
    List<QuestionVo> getQuestionByCategory(String category);

    /**
     * 根据答案获取题目
     * @param answers 答案List
     * @return
     */
    List<QuestionVo> batchGetQuestion(List<Answer> answers);

    /**
     * 添加题目
     * @param questionVo 题目
     * @return
     */
    boolean addQuestion(QuestionVo questionVo);
}
