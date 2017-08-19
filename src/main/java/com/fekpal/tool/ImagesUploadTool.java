package com.fekpal.tool;

import com.fekpal.cons.ResponseCode;
import com.fekpal.tool.BaseReturnData;
import com.fekpal.tool.FileUploadTool;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.java2d.opengl.WGLSurfaceData;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * 处理上传图片的工具类类
 * Created by hasee on 2017/8/16.
 */
public class ImagesUploadTool {

    /**
     * 上传头像的方法
     * @param myfiles 文件
     * @param request 请求
     * @param childrenPath 子目录如：club
     * @return 标准json数据
     */
    public static Map<String, Object> uploadImage(MultipartFile[] myfiles, HttpServletRequest request,String childrenPath) {
        //得到基本返回数据模板
        BaseReturnData returnData = new BaseReturnData();
        //判断图片格式和大小是否符合
        for(MultipartFile myfile : myfiles){
            if(!myfile.isEmpty()) {
                if (myfile.getSize() > 1024 * 1024 * 10) {
                    returnData.setStateCode(ResponseCode.REQUEST_ERROR, "图片大于10m请重新上传");
                    return returnData.getMap();
                }
                if (!myfile.getContentType().toString().equals("image/png")
                        && !myfile.getContentType().toString().equals("image/jpeg")
                        && !myfile.getContentType().toString().equals("image/jpg")
                        && !myfile.getContentType().toString().equals("image/bmp")) {
                    returnData.setStateCode(ResponseCode.REQUEST_ERROR, "上传的图片不符合格式，请重新上传");
                    return returnData.getMap();
                }
            }else {
                returnData.setStateCode(1, "没上传文件，请重新上传");
                return returnData.getMap();
            }
        }
        //如果上传的文件为空，返回提示语句
        if (myfiles != null) {
            //如果上传的文件不是空，那么将图片存入服务器中的与本项目同目录的//MySAUImages/club文件夹中
            List<String> fileNameList = FileUploadTool.imageHandle(myfiles, request, childrenPath);
            Map<String,String> clubLogoMap = new HashMap<String,String>();
            clubLogoMap.put("clubLogo",fileNameList.get(0));
            returnData.setData(clubLogoMap);
        }else{
            returnData.setStateCode(1, "没上传文件，请重新上传");
        }
        return returnData.getMap();
    }

}
