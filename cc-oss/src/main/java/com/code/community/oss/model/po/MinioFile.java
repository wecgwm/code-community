package com.code.community.oss.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.code.community.common.model.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("minio_file")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinioFile extends BasePo {
    private String url;
    private String fileType;
    private String fileInfo;
}
