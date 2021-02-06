package com.code.community.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.community.test.model.po.Answer;
import com.code.community.test.model.vo.AnswerVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
@Repository
public interface AnswerMapper extends BaseMapper<Answer> {
    List<AnswerVo> getAnswersByQuestionId(String questionId);

    List<AnswerVo> getCorrectAnswersByQuestionIds(@RequestParam("list") List<String> list);

    List<Answer> getAnswers(@RequestParam("list") List<String> list);
}
