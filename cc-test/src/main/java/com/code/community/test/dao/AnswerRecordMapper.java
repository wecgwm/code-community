package com.code.community.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.community.test.model.po.AnswerRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AnswerRecordMapper extends BaseMapper<AnswerRecord> {
}
