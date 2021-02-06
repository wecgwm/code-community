package com.code.community.oss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.community.oss.model.po.MinioFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
@Repository
public interface MinioFileMapper extends BaseMapper<MinioFile> {
    List<MinioFile> getImages(@RequestParam("list") List<String> list);
}
