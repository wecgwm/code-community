package com.code.community.blog.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.code.community.common.model.po.BasePo;
import lombok.Data;

@Data
@TableName("blog")
public class Blog extends BasePo {
    private static final long serialVersionUID = -6916257260379027817L;

    private String title;
    private String content;
    private String author;
    private Integer supportCount = 0;
    private String tag;

}
