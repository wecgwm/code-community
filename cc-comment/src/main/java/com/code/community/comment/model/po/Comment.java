package com.code.community.comment.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.code.community.common.model.po.BasePo;
import lombok.Data;

@Data
@TableName("comment")
public class Comment extends BasePo {
    private static final long serialVersionUID = 6079062762659473105L;

    private String userId;
    private Integer receiveType;
    private String receiveId;
    private String content;
}
