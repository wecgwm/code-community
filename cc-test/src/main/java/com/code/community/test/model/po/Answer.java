package com.code.community.test.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.code.community.common.model.po.BasePo;
import lombok.Data;

@Data
@TableName("answer")
public class Answer extends BasePo {
    private static final long serialVersionUID = -8985940839793627739L;

    private String content;
    private String questionId;
    private Integer correct;
}
