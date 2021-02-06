package com.code.community.oss.util;

import cn.hutool.core.lang.UUID;
import com.code.community.comment.base.constant.NumberConstant;
import com.code.community.comment.base.exception.BaseException;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MinioUtils {
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String SLASH = "/";

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.accessKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    private MinioClient getMinioClient() {
        try {
            return new MinioClient(endpoint, accessKey, secretKey);
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO 如果上传头像 删除原头像
     *
     * @param file
     * @return
     */
    public String uploadSingleFile(MultipartFile file) {
        MinioClient minioClient = getMinioClient();
        String url = "";
        try {
            if (!minioClient.bucketExists(bucketName)) {
                throw new BaseException("MinIO bucket不存在");
            }
            String oldFileName = file.getOriginalFilename();
            String fileType = FileUtils.getFileType(oldFileName);
            // 设置存储对象名称
            String newName = UUID.fastUUID() + fileType;
            // 根据日期设置路径
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String objectName = sdf.format(new Date()) + SLASH + newName;
            // 上传文件到存储桶中
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(bucketName, objectName, inputStream,
                    new PutObjectOptions(inputStream.available(), NumberConstant.MINUS_ONE));
            // 上传后文件的url
            url = endpoint + SLASH + bucketName + SLASH + objectName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

}
