package com.code.community.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.community.blog.model.po.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlogMapper extends BaseMapper<Blog> {
}
