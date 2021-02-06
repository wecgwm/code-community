package com.code.community.test.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.community.comment.base.constant.NumberConstant;
import com.code.community.comment.base.exception.CRUDException;
import com.code.community.comment.base.exception.CRUDExceptionType;
import com.code.community.test.dao.AnswerMapper;
import com.code.community.test.model.po.Answer;
import com.code.community.test.model.vo.AnswerVo;
import com.code.community.test.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {

    private static final Integer FALSE = 0;

    @Autowired
    AnswerMapper answerMapper;


    @Override
    public int getScoreByAnswers(List<AnswerVo> userAnswerVos) {
        ArrayList<String> ids = new ArrayList<>();
        userAnswerVos.forEach(answerVo -> {
            if (Objects.nonNull(answerVo)){
                ids.add(answerVo.getId());
            }
        });
        List<Answer> answers = this.listByIds(ids);
        if(CollectionUtil.isEmpty(answers)){
            throw new CRUDException(CRUDExceptionType.NOT_FOUND, "未找到对应答案记录");
        }
        //过滤错误答案
        answers.removeIf(answer->answer.getCorrect().equals(FALSE));
        //一道题10分
        return answers.size() * NumberConstant.TEN;
    }

    @Override
    public List<AnswerVo> getCorrectAnswersByQuestionIds(List<String> questionIds) {
        List<AnswerVo> correctAnswersByQuestionIds = answerMapper.getCorrectAnswersByQuestionIds(questionIds);
        if(correctAnswersByQuestionIds == null || correctAnswersByQuestionIds.size() != questionIds.size()){
            throw new RuntimeException();
        }
        return correctAnswersByQuestionIds;
    }

    @Override
    public List<Answer> batchGetAnswer(List<String> answerIds) {
        List<Answer> answers = answerMapper.getAnswers(answerIds);
        return answers;
    }

    //TODO
    @Override
    public boolean batchSaveAnswer(List<Answer> answers) {
        boolean b = this.saveBatch(answers);
        return b;
    }
}
