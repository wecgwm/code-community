package com.code.community.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.code.community.comment.base.constant.FiledConstant;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 字段自动填充器，基类注入
 */
public class BasePoMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, FiledConstant.CREATED_TIME_UP_LOW_CASE, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, FiledConstant.UPDATED_TIME_UP_LOW_CASE, LocalDateTime.class,LocalDateTime.now());
        this.strictInsertFill(metaObject,FiledConstant.STATE,Integer.class,FiledConstant.STATE_EFFECTIVE);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,FiledConstant.UPDATED_TIME,LocalDateTime.class,LocalDateTime.now());
    }
}
