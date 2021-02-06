package com.code.community.oss.model.vo;

import com.code.community.common.model.vo.BaseVo;
import com.code.community.oss.model.po.MinioFile;
import lombok.Data;

@Data
public class VideoVo extends BaseVo<MinioFile> {
    private static final long serialVersionUID = -71709462163364997L;

    private String id;
    private String url;
    private String fileInfo;
    private String imgUrl;
}
