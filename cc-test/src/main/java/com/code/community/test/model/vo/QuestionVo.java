package com.code.community.test.model.vo;

import com.code.community.common.model.vo.BaseVo;
import com.code.community.test.model.po.Question;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionVo extends BaseVo<Question> {
    private static final long serialVersionUID = -467401853747242776L;

    private String id;
    private String content;
    private List<AnswerVo> answers;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    public Question toQuestionPo(){
        Question question = BeanUtils.instantiateClass(Question.class);
        BeanUtils.copyProperties(this,question);
        return question;
    }
}
