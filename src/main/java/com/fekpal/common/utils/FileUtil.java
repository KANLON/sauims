package com.fekpal.common.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理文件的工具类
 * Created by hasee on 2017/8/16.
 */
public class FileUtil {

    /**
     * 处理上传的文件
     *
     * @param files    传输的文件集
     * @param savePath 存放路径
     * @return 返回文件名集
     */
    public static List<String> fileHandle(MultipartFile[] files, String savePath) throws IOException {
        return fileHandle(files, savePath, null);
    }

    /**
     * 处理上传的文件
     *
     * @param file     传输的文件
     * @param savePath 存放路径
     * @return 返回文件名
     */
    public static String fileHandle(MultipartFile file, String savePath) throws IOException {
        return fileHandle(file, savePath, null);
    }

    /**
     * 处理上传的文件
     *
     * @param files    传输的文件集
     * @param savePath 存放路径
     * @param fileName 后缀文件名
     * @return 返回文件名集
     */
    public static List<String> fileHandle(MultipartFile[] files, String savePath, String fileName) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String[] originalFileName = file.getOriginalFilename().split("\\.");
                fileName = (fileName == null || fileName.length() == 0) ? RandomUtil.createFileName() + "." + originalFileName[1] : fileName;
                file.transferTo(new File(savePath, fileName));
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }


    /**
     * 处理上传的文件
     *
     * @param file     传输的文件 MultipartFile
     * @param savePath 存放路径 如：c:/xxx/xxx/
     * @param fileName 不带后缀的文件名  如：xxx
     * @return 返回文件名
     */
    public static String fileHandle(MultipartFile file, String savePath, String fileName) throws IOException {
        if (file != null /*&& !file.isEmpty()*/) {
            String[] originalFileName = file.getOriginalFilename().split("\\.");
            fileName = (fileName == null || fileName.length() == 0)
                    ? RandomUtil.createFileName() + "." + originalFileName[originalFileName.length-1]
                    : fileName+ "." + originalFileName[originalFileName.length-1];
            file.transferTo(new File(savePath, fileName));
            return fileName;
        }
        return null;
    }

    /**
     * 下载文件的工具方法（应该涉及到线程安全问题，以后要改）
     * @param filePath 文件所在路径 如：c:/xxx/xxx/
     * @param fileName 文件名字 带后缀 如:xxx.docx
     * @param response 响应
     */
    public synchronized  static  void downFile(String filePath,String fileName,HttpServletResponse response){
        try {
            InputStream in = new FileInputStream(new File(filePath,fileName));
            response.setHeader("content-disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            byte[] byteBuffer = new byte[1024];
            int len = 0;
            while ((len = in.read(byteBuffer)) != -1) {
                out.write(byteBuffer, 0, len);
            }
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("文件找不到");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件找不到");
        }
    }

}
