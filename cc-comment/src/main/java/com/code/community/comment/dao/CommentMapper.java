package com.code.community.comment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.community.comment.model.po.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
}
