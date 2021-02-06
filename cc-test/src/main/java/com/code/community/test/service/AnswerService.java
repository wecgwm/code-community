package com.code.community.test.service;

import com.code.community.test.model.po.Answer;
import com.code.community.test.model.vo.AnswerVo;

import java.util.List;

public interface AnswerService {
    /**
     * 获取成绩
     * @param userAnswerVos 用户提交答案
     * @return
     */
    int getScoreByAnswers(List<AnswerVo> userAnswerVos);

    /**
     * 批量获取正确答案
     * @param questionIds 题目idList
     * @return
     */
    List<AnswerVo> getCorrectAnswersByQuestionIds(List<String> questionIds);

    /**
     * 批量获取答案
     * @param answerIds 答案idList
     * @return
     */
    List<Answer> batchGetAnswer(List<String> answerIds);

    /**
     * 批量添加答案
     * @param answers 答案List
     * @return
     */
    boolean batchSaveAnswer(List<Answer> answers);
}
