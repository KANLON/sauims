package com.fekpal.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 处理图片的工具类类
 * Created by hasee on 2017/8/16.
 */
public class ImageFileUtil {

    /**
     * 上传头像的方法
     *
     * @param files    文件
     * @param savePath 存放路径
     * @return 储存后的文件名或者null
     */
    public static List<String> handle(MultipartFile[] files, String savePath) throws IOException {
        return FileUtil.fileHandle(files, savePath);
    }

    /**
     * 上传头像的方法
     *
     * @param file     文件
     * @param savePath 存放路径
     * @return 储存后的文件名或者null
     */
    public static String handle(MultipartFile file, String savePath) throws IOException {
        return FileUtil.fileHandle(file, savePath);
    }

    /**
     * 上传头像的方法
     *
     * @param files    文件
     * @param savePath 存放路径
     * @param fileName 不含后缀文件名
     * @return 储存后的文件名或者null
     */
    public static List<String> handle(MultipartFile[] files, String savePath, String fileName) throws IOException {
        return FileUtil.fileHandle(files, savePath, fileName);
    }

    /**
     * 上传头像的方法
     *
     * @param file     文件
     * @param savePath 存放路径
     * @param fileName 不含后缀文件名
     * @return 储存后的文件名或者null
     */
    public static String handle(MultipartFile file, String savePath, String fileName) throws IOException {
        return FileUtil.fileHandle(file, savePath, fileName);
    }

    /**
     * 判断图片格式合法性
     *
     * @param file 文件
     * @return 是否正确
     */
    public static boolean isValid(MultipartFile file) {
        return (file != null
                && !file.getContentType().equals("image/png")
                && !file.getContentType().equals("image/jpeg")
                && !file.getContentType().equals("image/jpg")
                && !file.getContentType().equals("image/bmp"));
    }
}
