package com.fekpal.web.controller;

import com.fekpal.tool.BaseReturnData;
import com.fekpal.tool.ImagesUploadTool;
import com.sun.org.apache.regexp.internal.REUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TooManyListenersException;

import static java.lang.System.out;

/**
 * 校社联中心信息的控制类
 * Created by hasee on 2017/8/19.
 */
@Controller
public class SauCenterController {

    /**
     * 得到校社联中心的信息的方法
     * @param session 用户session
     * @return 校社联的一些基本信息
     */
    @RequestMapping("/sau/center/info")
    @ResponseBody
    public Map<String,Object> getSauCenterMsg(HttpSession session){
        BaseReturnData returnData = new BaseReturnData();
        //要在拦截器中判断用户是否登录了然后判断是否有这个权限
        // TODO: 2017/8/19

        //用户ID
        int userId;
        if(session.getAttribute("userCode")==null) {
            out.println("用户还没有登录");
        }else{
            //从session中得到用户ID
            userId = (Integer) session.getAttribute("userCode");
        }
        //创建链表map集合存放校社联中心信息
        Map<String,Object> sauCenterMsg = new LinkedHashMap<String, Object>();

        //模拟成立时间
        Date foundTime;
        try {
            foundTime= new SimpleDateFormat("yyyy-MM-dd").parse("2017-10-10");
        } catch (ParseException e) {
            e.printStackTrace();
            returnData.setStateCode(1,"内部得到时间错误");
            return returnData.getMap();
        }

        //模拟service层通过用户ID得到数据
        // TODO: 2017/8/19
        sauCenterMsg.put("clubName","校校社联");
        sauCenterMsg.put("foundTime",foundTime);
        sauCenterMsg.put("description","这是校社联的描述");
        sauCenterMsg.put("adminName","张三");
        sauCenterMsg.put("members",100);
        sauCenterMsg.put("clubDutyNumber",20);
        //从数据库中取出文件名
        sauCenterMsg.put("clubLogo","a.jpg");

        //把用户数据添加到返回数据模板中
        returnData.setData(sauCenterMsg);

        return returnData.getMap();
    }

    /**
     * 上传校社联头像的方法
     * @param myfiles 文件对象，用from-data表单
     * @param request 请求
     * @return 图片文件名
     */
    @ResponseBody
    @RequestMapping("/sau/center/info/edit/head")
    public Map<String,Object> uploadLogo(@RequestParam("myfiles") MultipartFile[] myfiles, HttpServletRequest request){

        Map<String,Object> returnData = ImagesUploadTool.uploadImage(myfiles,request,"sau");

        //初始化校社联头像文件名
        String clubLogoName="";
        if("0".equals(returnData.get("code").toString())) {
            Map<String,String> clubLogoNameMap =(Map<String, String>) returnData.get("data");
            clubLogoName = clubLogoNameMap.get("clubLogo");

            //根据用户id将logo文件名存入数据库
            //得到用户id
            HttpSession session = request.getSession();

            //模拟用户已经登录
            session.setAttribute("userCode", 1);

            if (session.getAttribute("userCode") != null) {
                int userId = (Integer) session.getAttribute("userCode");
                out.println("用户ID为：" + userId);
            } else {
                out.println("用户还没有登录");
                throw new RuntimeException("用户还没有登录");
            }
            //将logo文件名存入数据库
            // TODO: 2017/8/19
            out.println("存入数据库logo的文件名：" + clubLogoName);
        }

        return returnData;
    }
}
