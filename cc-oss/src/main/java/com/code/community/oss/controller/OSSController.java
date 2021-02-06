package com.code.community.oss.controller;

import com.code.community.comment.base.model.vo.Result;
import com.code.community.common.model.PageInfo;
import com.code.community.oss.model.po.MinioFile;
import com.code.community.oss.service.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/oss")
@Slf4j
@Api("文件相关接口")
public class OSSController {

    @Autowired
    private MinioService minioService;

    @ApiOperation(value = "上传单个文件",notes = "用户上传头像")
    @PostMapping
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam(value = "fileInfo",required = false)String fileInfo) {
        log.info("upload start:{}",fileInfo);
        MinioFile minioFile = minioService.uploadSingleFile(file,fileInfo);
        List<MinioFile> list = Arrays.asList(minioFile);
        return Result.success(list);
    }

    @ApiOperation("获取视频信息list")
    @GetMapping
    public Result getVideos(@Validated PageInfo pageInfo){
        log.info("getVideoVo,pageInfo:{}",pageInfo);
        return Result.success(minioService.getVideos(pageInfo));
    }

    //TODO
    @ApiOperation("上传视频以及视频缩略图")
    @PostMapping("/video")
    public Result uploadVideo(@RequestParam("video")MultipartFile video,
                              @RequestParam("img")MultipartFile img,
                              @RequestParam String videoTitle){
        log.info("upload video:{}",videoTitle);
        minioService.uploadVideo(video,img,videoTitle);
        return Result.success();
    }

}
