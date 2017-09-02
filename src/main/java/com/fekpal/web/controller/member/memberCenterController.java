package com.fekpal.web.controller.member;

import com.fekpal.cons.ResponseCode;
import com.fekpal.tool.BaseReturnData;
import com.fekpal.tool.ImagesUploadTool;
import com.fekpal.tool.MailHtmlTool;
import com.fekpal.tool.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

/**
 * 普通用户和社团成员端中心的控制类
 * Created by hasee on 2017/8/19.
 */
@Controller
public class MemberCenterController {

    //spring的依赖注入发送邮件对象
    @Autowired
    private MailHtmlTool mailHtmlTool;

    /**
     * 得到普通成员和社团成员中心的信息的方法
     *
     * @param session 用户session
     * @return 普通成员或者社团成员的一些基本信息
     */
    @ResponseBody
    @RequestMapping("/menber/center/info")
    public Map<String, Object> getMemberCenterMsg(HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();
        //要在拦截器中判断用户是否登录了然后判断是否有这个权限
        // TODO: 2017/8/19

        //得到用户ID
        int userId;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        //创建链表map集合存放普通成员中心信息
        Map<String, Object> memberCenterMsg = new LinkedHashMap<String, Object>();

        //模拟service层通过用户ID得到数据
        // TODO: 2017/8/19
        //模拟数据
        memberCenterMsg.put("userName", "s19961234@126.com");
        memberCenterMsg.put("realName", "张三");
        //从数据库中取出文件名
        memberCenterMsg.put("personLogo", "a.jpg");
        memberCenterMsg.put("studentID", "151612220");
        memberCenterMsg.put("gender", "男");
        memberCenterMsg.put("birthday", new Date());
        memberCenterMsg.put("phone", "18316821333");
        memberCenterMsg.put("phone", "18316821822");
        memberCenterMsg.put("joinTime", new Date());
        memberCenterMsg.put("leaveTime", new Date());
        memberCenterMsg.put("departmentName", "金融学院与统计学院");
        memberCenterMsg.put("majorName", "信息与计算科学");
        memberCenterMsg.put("address", "8#111");
        //创建社团成员所属社团的list集合
        List<Map<String, Object>> clubsList = new ArrayList<Map<String, Object>>();
        Map<String, Object> clubsMap1 = new LinkedHashMap<String, Object>();
        clubsMap1.put("clubName", "乒乓球协会");
        clubsMap1.put("clubDuty", 0);
        clubsMap1.put("userState", 1);
        Map<String, Object> clubsMap2 = new LinkedHashMap<String, Object>();
        clubsMap2.put("clubName", "羽毛球协会");
        clubsMap2.put("clubDuty", 1);
        clubsMap2.put("userState", 1);
        clubsList.add(clubsMap1);
        clubsList.add(clubsMap2);

        memberCenterMsg.put("clubs", clubsList);

        //把用户数据添加到返回数据模板中
        returnData.setData(memberCenterMsg);

        return returnData.getMap();
    }

    /**
     * 上传社团头像的方法
     *
     * @param myfiles 文件对象，用from-data表单
     * @param request 请求
     * @return 图片文件名
     */
    @ResponseBody
    @RequestMapping(value = "/club/center/info/edit/head", method = RequestMethod.PUT)
    public Map<String, Object> uploadLogo(@RequestParam("file") MultipartFile[] myfiles, HttpServletRequest request, HttpSession session) {
        Map<String, Object> returnData = ImagesUploadTool.uploadImage(myfiles, request, "club//logo");

        //初始化社团头像文件名
        String memberLogoName = "";
        if ("0".equals(returnData.get("code").toString())) {
            Map<String, String> memberLogoNameMap = (Map<String, String>) returnData.get("data");
            memberLogoName = memberLogoNameMap.get("clubLogo");

            if (session.getAttribute("userCode") != null) {
                int userId = (Integer) session.getAttribute("userCode");
                out.println("用户ID为：" + userId);
            } else {
                out.println("用户还没有登录");
                throw new RuntimeException("用户还没有登录");
            }
            //将logo文件名存入数据库
            // TODO: 2017/8/19
            out.println("存入数据库logo的文件名：" + memberLogoName);
        }

        return returnData;
    }

    /**
     * 上传用户个人头像的方法
     *
     * @param myfiles 文件对象，用from-data表单
     * @param request 请求
     * @return 图片文件名
     */
    @ResponseBody
    @RequestMapping(value = "/member/center/info/edit/head", method = RequestMethod.PUT)
    public Map<String, Object> uploadView(@RequestParam("file") MultipartFile[] myfiles, HttpServletRequest request, HttpSession session) {
        Map<String, Object> returnData = ImagesUploadTool.uploadImage(myfiles, request, "member//Logo");
        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        //初始化用户头像文件名
        String memberLogoName = "";
        //如果上传头像没有错误
        if ("0".equals(returnData.get("code").toString())) {
            Map<String, String> memberLogoNameMap = (Map<String, String>) returnData.get("data");
            memberLogoName = memberLogoNameMap.get("clubLogo");


            //将logo文件名存入数据库
            // TODO: 2017/8/19
            out.println("存入数据库个人头像logo的文件名：" + memberLogoName);
        }

        //如果用户有错误，则直接返回错误信息
        return returnData;
    }

    /**
     * 普通成员或者社团成员用来提交修改个人中心的信息
     *
     * @param memberCenterMsg 个人中心信息
     * @param request         请求
     * @param session         会话
     * @return 是否提交成功
     */
    @ResponseBody
    @RequestMapping(value = "/member/center/info/edit", method = RequestMethod.PUT)
    public Map<String, Object> subNewCenterMsg(@RequestParam Map<String, Object> memberCenterMsg, HttpServletRequest request, HttpSession session) {
        out.println(memberCenterMsg);
        //初始化返回数据模板
        BaseReturnData returnData = new BaseReturnData();

        //初始化session内的邮箱，验证码和用户id
        String sessionEmail = null;
        String sessionCaptcha = null;
        String dateStr = "";
        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        // 用工具类来判断输入的字段是否符合要求，邮箱之类的
        // TODO: 2017/8/21

        //处理时间格式问题
        // TODO: 2017/9/2

        //将用户存入数据库
        out.println("用户id:" + userId);
        out.println("根据用户id存入数据库" + memberCenterMsg);

        return returnData.getMap();
    }
}












