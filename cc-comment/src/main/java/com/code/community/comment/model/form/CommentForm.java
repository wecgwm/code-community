package com.code.community.comment.model.form;

import com.code.community.comment.model.po.Comment;
import com.code.community.common.model.form.BaseForm;
import com.code.community.common.validator.annotion.IdValid;
import com.code.community.common.validator.group.Delete;
import com.code.community.common.validator.group.Insert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("评论表单")
@Data
public class CommentForm extends BaseForm<Comment> {

    @ApiModelProperty("评论ID")
    @IdValid(groups = {Delete.class})
    private String id;

    @ApiModelProperty("评论用户ID")
    @NotNull(groups = {Insert.class})
    private String userId;

    @ApiModelProperty("被评论内容的ID，例如博客ID")
    @NotNull(groups = {Insert.class})
    private String receiveId;

    @ApiModelProperty("TODO 被评论内容类型")
    //@NotBlank(groups = {Insert.class})
    private String receiveType;

    @ApiModelProperty("评论内容")
    @NotBlank(groups = {Insert.class})
    private String content;


}
