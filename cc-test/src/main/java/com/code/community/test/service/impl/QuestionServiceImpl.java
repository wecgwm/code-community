package com.code.community.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.community.comment.base.constant.FiledConstant;
import com.code.community.comment.base.constant.NumberConstant;
import com.code.community.comment.base.exception.BaseException;
import com.code.community.common.factory.VoFactory;
import com.code.community.test.dao.QuestionMapper;
import com.code.community.test.model.po.Answer;
import com.code.community.test.model.po.Question;
import com.code.community.test.model.vo.AnswerVo;
import com.code.community.test.model.vo.QuestionVo;
import com.code.community.test.service.AnswerService;
import com.code.community.test.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    AnswerService answerService;


    @Override
    public List<QuestionVo> getQuestionByCategory(String category) {
        List<QuestionVo> questionByCategory = questionMapper.getQuestionByCategory(category);
        if (questionByCategory == null ||questionByCategory.size() < NumberConstant.TEN){
            throw new BaseException("题目不足");
        }
        return questionByCategory;
    }

    @Override
    public List<QuestionVo> batchGetQuestion(List<Answer> answers) {
        List<String> questionIds = answers.stream().map(Answer::getQuestionId).collect(Collectors.toList());
        List<Question> questions = this.listByIds(questionIds);
        List<QuestionVo> collect = questions.stream().map(question ->
                VoFactory.createVo(QuestionVo.class, question)
        ).collect(Collectors.toList());
        return collect;
    }

    //TODO
    @Override
    public boolean addQuestion(QuestionVo questionVo) {
        Question question = questionVo.toQuestionPo();
        List<AnswerVo> answerVos = questionVo.getAnswers();
        question.setQuestionCategory("java");
        List<Answer> answers = answerVos.stream().map(AnswerVo::toAnswerPo).collect(Collectors.toList());
        boolean save = this.save(question);
        answers.forEach(answer -> answer.setQuestionId(question.getId()));
        boolean batchSaveAnswer = answerService.batchSaveAnswer(answers);
        return save && batchSaveAnswer;
    }

    @Override
    public Set<String> getCategory() {
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.select(FiledConstant.QUESTION_CATEGORY);
        Set<String> collect = this.listObjs(questionQueryWrapper).
                stream().map(o -> (String) o).collect(Collectors.toSet());
        return collect;
    }
}
