package com.code.community.comment.model.vo;


import com.code.community.user.model.vo.UserVo;
import com.code.community.comment.model.po.Comment;
import com.code.community.common.model.vo.BaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel
@Data
public class CommentVo extends BaseVo<Comment> {

    private static final long serialVersionUID = 1245124621535063889L;

    @ApiModelProperty("评论id")
    private String id;

    @ApiModelProperty("评论用户ID")
    private String userId;

    @ApiModelProperty("被评论内容的ID，例如博客ID")
    private String receiveId;

    @ApiModelProperty("TODO 被评论内容类型")
    private String receiveType;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论用户")
    private UserVo userVo;

    @ApiModelProperty("评论创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

}
