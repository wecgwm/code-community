package com.code.community.test.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.code.community.common.model.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("answer_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRecord extends BasePo {
    private static final long serialVersionUID = 6548755792213506925L;

    private String answerId;
    private String userId;
}
