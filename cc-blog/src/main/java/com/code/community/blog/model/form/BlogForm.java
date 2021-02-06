package com.code.community.blog.model.form;

import com.code.community.blog.model.po.Blog;
import com.code.community.common.model.form.BaseForm;
import com.code.community.common.validator.annotion.IdValid;
import com.code.community.common.validator.group.Insert;
import com.code.community.common.validator.group.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class BlogForm extends BaseForm<Blog> {

    @ApiModelProperty("博客id")
    @IdValid(groups = {Update.class})
    private String id;

    @ApiModelProperty("博客标题")
    @NotBlank(groups = {Insert.class, Update.class})
    private String title;

    @ApiModelProperty("博客内容")
    @NotBlank(groups = {Insert.class, Update.class})
    private String content;

    @ApiModelProperty("博客作者")
    @NotBlank(groups = {Insert.class, Update.class})
    private String author;

    @ApiModelProperty("博客tag")
    private String tag;

}
