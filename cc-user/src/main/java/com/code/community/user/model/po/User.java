package com.code.community.user.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.code.community.common.model.po.BasePo;
import lombok.Data;

@TableName("users")
@Data
public class User extends BasePo {
    private static final long serialVersionUID = -5769487892388453532L;

    private String userName;
    private String password;
    private Integer gender;
    private String summary;
    /**
     * 更新时null属性不跳过，即数据库字段属性置为null(实现删除头像)
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String avatarUrl;
    private String direction;
}
