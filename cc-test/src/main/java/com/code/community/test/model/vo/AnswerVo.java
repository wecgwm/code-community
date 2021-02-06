package com.code.community.test.model.vo;

import com.code.community.common.model.vo.BaseVo;
import com.code.community.test.model.po.Answer;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class AnswerVo extends BaseVo<Answer> {
    private static final long serialVersionUID = -2587214782395633312L;

    private String id;
    private String content;

    public Answer toAnswerPo(){
        Answer answer = BeanUtils.instantiateClass(Answer.class);
        BeanUtils.copyProperties(this,answer);
        return answer;
    }
}
