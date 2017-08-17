package com.fekpal.web.controller;

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
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * 处理上传图片的的类
 * Created by hasee on 2017/8/16.
 */
@Controller
@RequestMapping("/sau/center/info/edit")
public class ImagesUpload {

    /**
     * 上传校社联中心的头像
     */
    @RequestMapping("/head")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("myfiles") MultipartFile[] myfiles, HttpServletRequest request) {
        //得到基本返回数据模板
        BaseReturnData returnData = new BaseReturnData();

        if (myfiles != null) {
            //如果上传的文件不是空，那么将图片存入服务器中的与本项目同目录的//MySAUImages/club文件夹中
            List<String> fileUrlStr = FileUploadTool.imageHandle(myfiles, request, "club");
            returnData.setData(fileUrlStr);

        }
        //如果上传的文件为空，返回提示语句
        if (myfiles != null) {
            for (MultipartFile myfile : myfiles) {
                if (myfile.isEmpty()) {
                    returnData.setStateCode(1, "没上传文件，请重新上传");
                }
            }

        }else{
            returnData.setStateCode(1, "没上传文件，请重新上传");
        }
        return returnData.getMap();
    }

}
