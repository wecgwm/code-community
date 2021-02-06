package com.code.community.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.community.user.model.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}
