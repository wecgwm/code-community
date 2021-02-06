package com.code.community.user.model.vo;

import com.code.community.user.model.po.User;
import com.code.community.common.model.vo.BaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel
public class UserVo extends BaseVo<User> {
    private static final long serialVersionUID = -8000040688646112791L;

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("用户名")
    private String UserName;

    @ApiModelProperty("用户性别，0男1女")
    private Integer gender;

    @ApiModelProperty("用户简介")
    private String summary;

    @ApiModelProperty("用户学习方向")
    private String direction;

    @ApiModelProperty("用户头像url")
    private String avatarUrl;

    //shape指定序列化后的类型
    @ApiModelProperty("用户创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

}
