package com.code.community.oss.util;

import org.springframework.stereotype.Component;

@Component
public class FileUtils {

    /**
     * 获取文件类型后缀
     * 例如.mp4
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName){
        if (fileName.lastIndexOf(".") == -1){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") );
    }

}
