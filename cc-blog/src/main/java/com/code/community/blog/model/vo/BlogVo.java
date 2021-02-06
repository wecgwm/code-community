package com.code.community.blog.model.vo;

import com.code.community.blog.model.po.Blog;
import com.code.community.common.model.vo.BaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel
@Data
public class BlogVo extends BaseVo<Blog> {
    private static final long serialVersionUID = -8478041382806729725L;

    @ApiModelProperty("博客标题")
    private String id;

    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("博客内容")
    private String content;

    @ApiModelProperty("博客作者")
    private String author;

    @ApiModelProperty("博客点赞数")
    private Integer supportCount;

    @ApiModelProperty("博客标签")
    private String tag;

    @ApiModelProperty("博客创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;


}
