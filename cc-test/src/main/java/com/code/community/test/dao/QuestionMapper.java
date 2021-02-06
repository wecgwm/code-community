package com.code.community.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.community.test.model.po.Question;
import com.code.community.test.model.vo.QuestionVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper extends BaseMapper<Question> {
    List<QuestionVo> getQuestionByCategory(String category);
}
