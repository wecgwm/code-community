package com.code.community.user.model.form;

import com.code.community.common.validator.group.Get;
import com.code.community.user.model.po.User;
import com.code.community.common.model.form.BaseForm;
import com.code.community.common.validator.annotion.IdValid;
import com.code.community.common.validator.group.Insert;
import com.code.community.common.validator.group.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class UserForm extends BaseForm<User> {

    @ApiModelProperty
    @IdValid(groups = {Update.class})
    private String id;

    @ApiModelProperty("用户名")
    @NotBlank(groups = {Insert.class, Get.class})
    @Length(min = 3, max = 8, message = "用户名必须大于3个字符小于8个字符")
    private String userName;

    @ApiModelProperty("用户密码")
    @NotBlank(groups = {Insert.class,Get.class})
    @Length(min = 8, max = 16, message = "用户密码必须大于8个字符小于16个字符")
    private String password;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("学习方向")
    private String direction;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("头像url")
    private String avatarUrl;
}
