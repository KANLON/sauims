package com.fekpal.web.controller.clubAdmin;

import com.fekpal.cons.ResponseCode;
import com.fekpal.tool.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.System.out;

/**
 * 校社联中心信息的控制类
 * Created by hasee on 2017/8/19.
 */
@Controller
public class ClubCenterController {

    //spring的依赖注入发送邮件对象
    @Autowired
    private MailHtmlTool mailHtmlTool;

    /**
     * 得到社团中心的信息的方法
     * @param session 用户session
     * @return 社团的一些基本信息
     */
    @ResponseBody
    @RequestMapping("/club/center/info")
    public Map<String,Object> getClubsCenterMsg(HttpSession session){
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
        //创建链表map集合存放社团中心信息
        Map<String,Object> clubCenterMsg = new LinkedHashMap<String, Object>();

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
        clubCenterMsg.put("clubId",123);
        clubCenterMsg.put("clubName","乒乓球协会");
        //从数据库中取出文件名
        clubCenterMsg.put("clubLogo","a.jpg");
        clubCenterMsg.put("clubView","b.jpg");
        clubCenterMsg.put("description","这是乒乓球协会的描述");
        clubCenterMsg.put("adminName","张三");
        clubCenterMsg.put("email","s19961234@126.com");
        clubCenterMsg.put("phone","18316821822");
        clubCenterMsg.put("foundTime",foundTime);
        clubCenterMsg.put("members",100);

        //把用户数据添加到返回数据模板中
        returnData.setData(clubCenterMsg);

        return returnData.getMap();
    }

    /**
     * 上传社团头像的方法
     * @param myfiles 文件对象，用from-data表单
     * @param request 请求
     * @return 图片文件名
     */
    @ResponseBody
    @RequestMapping(value = "/club/center/info/edit/head",method = RequestMethod.PUT)
    public Map<String,Object> uploadLogo(@RequestParam("file") MultipartFile[] myfiles,HttpServletRequest request){
        Map<String,Object> returnData = ImagesUploadTool.uploadImage(myfiles,request,"club//logo");

        //初始化社团头像文件名
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

    /**
     * 上传社团展示图片的方法
     * @param myfiles 文件对象，用from-data表单
     * @param request 请求
     * @return 图片文件名
     */
    @ResponseBody
    @RequestMapping(value = "/club/center/info/edit/view",method = RequestMethod.PUT)
    public Map<String,Object> uploadView(@RequestParam("file") MultipartFile[] myfiles,HttpServletRequest request){
        Map<String,Object> returnData = ImagesUploadTool.uploadImage(myfiles,request,"club//view");

        //初始化社团展示文件名
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

    /**
     * 社团用来提交修改社团中心的信息
     * @param clubCenterMsg 社团中心信息
     * @param request 请求
     * @param session 会话
     * @return 是否提交成功
     */
    @ResponseBody
    @RequestMapping(value = "/club/center/info/edit",method = RequestMethod.PUT)
    public Map<String,Object> subNewCenterMsg(@RequestParam Map<String,Object> clubCenterMsg,HttpServletRequest request,HttpSession session){
        out.println(clubCenterMsg);
        //初始化返回数据模板
        BaseReturnData returnData = new BaseReturnData();

        //初始化session内的邮箱，验证码和用户id
        String sessionEmail = null;
        String sessionCaptcha =null;
        int userId =0 ;
        String dateStr;

        // 用工具类来判断输入的字段是否符合要求，邮箱之类的
        // TODO: 2017/8/21

        //如果时间不为空
        if(!StringUtils.isEmpty(clubCenterMsg.get("foundTime"))) {
            //处理时间格式
            dateStr = (String) clubCenterMsg.get("foundTime");
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            out.println("时间为：" + date);
        }

        //从拦截器判断对象是否登陆
        if(session.getAttribute("userCode")!=null) {
            //从session域中取得用户id
            userId = (Integer) session.getAttribute("userCode");

            //根据用户id从service层得到数据库的用户的实体
            // TODO: 2017/8/20

            if (!StringUtils.isEmpty(clubCenterMsg.get("email")) && !StringUtils.isEmpty(clubCenterMsg.get("captcha")) ) {
                //如果session域内的邮箱验证码或者邮箱为空
                if(session.getAttribute("emailCaptcha")!=null && session.getAttribute("email")!=null) {
                    //从session域中得到验证码和邮箱
                    sessionCaptcha = (String) session.getAttribute("emailCaptcha");
                    sessionEmail = (String) session.getAttribute("email");
                }else{
                    returnData.setStateCode(ResponseCode.REQUEST_ERROR,"还没有发送邮箱验证码");
                    return returnData.getMap();
                }
                //校验验证码和邮箱是否相等和时间是否过了10分钟
                if (sessionCaptcha.trim().toLowerCase().equals(clubCenterMsg.get("captcha").toString().trim().toLowerCase())
                        && sessionEmail.trim().toLowerCase().equals(clubCenterMsg.get("email").toString().trim().toLowerCase())
                        && TimeTool.cmpTime((String) session.getAttribute("time"))) {
                    //将数据存入数据库
                    out.println("用户id:"+userId);
                    out.println("根据用户id存入数据库（包含邮箱地址）"+clubCenterMsg);

                }else{
                    returnData.setStateCode(ResponseCode.REQUEST_ERROR,"验证码错误或者验证码已过期，请重新输入");
                    return returnData.getMap();
                }
            }else if(StringUtils.isEmpty(clubCenterMsg.get("email")) && !StringUtils.isEmpty(clubCenterMsg.get("captcha")) ){
                returnData.setStateCode(ResponseCode.REQUEST_ERROR,"新邮箱为空，请输入新邮箱");
                return returnData.getMap();
            }else if(!StringUtils.isEmpty(clubCenterMsg.get("email")) && StringUtils.isEmpty(clubCenterMsg.get("captcha"))){
                returnData.setStateCode(ResponseCode.REQUEST_ERROR,"验证码为空，请输入验证码");
                return returnData.getMap();
            }else {

                //将用户存入数据库
                out.println("用户id:"+userId);
                out.println("根据用户id存入数据库（没有邮箱地址）"+clubCenterMsg);


            }
        }else{
            returnData.setStateCode(1,"用户还没有登陆，请登陆后再提交数据");
        }
        return returnData.getMap();
    }
}
