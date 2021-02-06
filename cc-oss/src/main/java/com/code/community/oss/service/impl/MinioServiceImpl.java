package com.code.community.oss.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.community.comment.base.constant.NumberConstant;
import com.code.community.comment.base.exception.CRUDException;
import com.code.community.comment.base.exception.CRUDExceptionType;
import com.code.community.common.factory.VoFactory;
import com.code.community.common.model.PageInfo;
import com.code.community.oss.dao.MinioFileMapper;
import com.code.community.oss.model.po.MinioFile;
import com.code.community.oss.model.vo.VideoVo;
import com.code.community.oss.service.MinioService;
import com.code.community.oss.util.FileUtils;
import com.code.community.oss.util.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "oss")
public class MinioServiceImpl extends ServiceImpl<MinioFileMapper, MinioFile> implements MinioService {

    private static final String MP4 = ".mp4";

    @Autowired
    private MinioUtils minioUtil;
    @Autowired
    private MinioFileMapper minioFileMapper;

    @Override
    public MinioFile uploadSingleFile(MultipartFile file, String file_info) {
        String url = minioUtil.uploadSingleFile(file);
        String fileType = FileUtils.getFileType(file.getOriginalFilename());
        MinioFile minioFile = new MinioFile(url, fileType, file_info);
        boolean save = this.save(minioFile);
        if (!save) {
            throw new CRUDException(CRUDExceptionType.INSERT_FAILED, "文件记录插入失败");
        }
        return minioFile;
    }

    @Override
    @Transactional
    public void uploadVideo(MultipartFile video, MultipartFile img, String videoTitle) {
        MinioFile uploadVideo = uploadSingleFile(video, videoTitle);
        uploadSingleFile(img, uploadVideo.getId());
    }

    @Cacheable(key = "#pageInfo.currentPage")
    @Override
    public List<VideoVo> getVideos(PageInfo pageInfo) {
        //查询视频链接
        List<MinioFile> videos = this.lambdaQuery().eq(MinioFile::getFileType, MP4).list();
        if (CollectionUtil.isEmpty(videos)) {
            return null;
        }
        //转换成VideoVo
        List<VideoVo> videoVos = videos.stream()
                .map(video -> VoFactory.createVo(VideoVo.class, video))
                .collect(Collectors.toList());
        //查询视频预览图
        List<String> ids = new ArrayList<>(NumberConstant.TEN);
        videos.forEach(video -> ids.add(video.getId()));
        List<MinioFile> images = minioFileMapper.getImages(ids);
        if (CollectionUtil.isEmpty(images)) {
            log.warn("没有找到预览图");
            return videoVos;
        }
        //设置预览图url到VideoVo中
        Map<String, String> vIdUrlMap = new HashMap<>(16);
        images.forEach(image -> {
            vIdUrlMap.put(image.getFileInfo(), image.getUrl());
        });
        videoVos.forEach(videoVo -> {
            videoVo.setImgUrl(vIdUrlMap.get(videoVo.getId()));
        });
        return videoVos;
    }
}
