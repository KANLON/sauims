package com.fekpal.tool;

import com.fekpal.cons.WebPath;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理上传文件或图片的工具类
 * Created by hasee on 2017/8/16.
 */
public class FileUploadTool {

    /**
     * 处理图片
     * @param myfiles 传输的文件
     * @param request 请求
     * @param childrenPath 放到哪个子目录去，不能为空
     * @return 返回文件访问路径
     */
    public static List<String> imageHandle(MultipartFile[] myfiles, HttpServletRequest request,String childrenPath){
        //处理上传图片
        List<String> imgPathList = new ArrayList<String>();
        //存放放在服务器的文件名
        List<String> fileNameList = new ArrayList<String>();
        for(MultipartFile myfile : myfiles){
            if(myfile.isEmpty()){
//                System.out.println("文件未上传");
                return null;
            }else{
/*                System.out.println("文件长度: " + myfile.getSize());
                System.out.println("文件类型: " + myfile.getContentType());
                System.out.println("文件名称: " + myfile.getName());
                System.out.println("文件原名: " + myfile.getOriginalFilename());
                System.out.println("========================================");*/

                String realPath = "D://masterspring"+"//MySAUImages//"+childrenPath;
                //使用自定义文件资源库     正式发布后，在确定图片放哪里后，才用这个
                //String realPath = WebPath.rootParentPath+"//MySAUImages//"+childrenPath;

                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
                try {
                    //重置文件名
                    long time = System.currentTimeMillis();
                    String timeStr = String.valueOf(time);
                    String[] originalFileName = myfile.getOriginalFilename().split("\\.");
                    String fileName = timeStr+"."+originalFileName[1];
                    FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, fileName));
                    //配置图片访问路径
                    String ip = "http://localhost:8080/MySAUImages";
                    imgPathList.add(ip+"/"+childrenPath+"/"+fileName);
                    fileNameList.add(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
//        return imgPathList;
        return fileNameList;
    }
}
