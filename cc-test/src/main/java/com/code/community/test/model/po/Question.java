package com.code.community.test.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.code.community.common.model.po.BasePo;
import lombok.Data;

@TableName("question")
@Data
public class Question extends BasePo {
    private static final long serialVersionUID = 774731700466861010L;

    private String content;
    private String questionCategory;
}
