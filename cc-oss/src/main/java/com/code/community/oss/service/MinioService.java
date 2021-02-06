package com.code.community.oss.service;

import com.code.community.common.model.PageInfo;
import com.code.community.oss.model.po.MinioFile;
import com.code.community.oss.model.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MinioService {
    /**
     * 上传单个文件
     * @param file 文件
     * @param file_info 文件附加信息,头像非必须
     * @return
     */
    MinioFile uploadSingleFile(MultipartFile file,String file_info);

    /**
     * 获取视频文件List
     * @param pageInfo 分页信息
     * @return
     */
    List<VideoVo> getVideos(PageInfo pageInfo);

    /**
     * 上传视频以及缩略图
     * @param video 视频文件
     * @param img 缩略图文件
     * @param videoTitle 视频标题
     */
    void uploadVideo(MultipartFile video, MultipartFile img, String videoTitle);
}
