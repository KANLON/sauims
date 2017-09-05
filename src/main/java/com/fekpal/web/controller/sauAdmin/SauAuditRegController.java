package com.fekpal.web.controller.sauAdmin;

import com.fekpal.cons.ResponseCode;
import com.fekpal.cons.WebPath;
import com.fekpal.tool.BaseReturnData;
import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.prefs.BackingStoreException;

import static java.lang.System.lineSeparator;
import static java.lang.System.out;

/**
 * 注册审核的控制类
 * Created by hasee on 2017/8/27.
 */
@Controller
public class SauAuditRegController {
    /**
     * 查看全部审核的信息的方法
     * @param session 会话
     * @return 审核信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/sau/audit/reg", method = RequestMethod.GET)
    public Map<String, Object> getAllAuditMsg(HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();

        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        //从dao中根据用户id得到社团或成员注册信息的对象，并获取该对象属性
        // TODO: 2017/8/27
        //模拟数据
        //将得到的数据放入每个map集合中
        Map<String, Object> auditMsgListMap1 = new LinkedHashMap<String, Object>();
        auditMsgListMap1.put("auditMsgId", 234);
        auditMsgListMap1.put("auditTitle", "乒乓球协会");
        auditMsgListMap1.put("registerName", "乒乓球协会");
        auditMsgListMap1.put("registerTime", new Date());
        auditMsgListMap1.put("auditState", 2);
        auditMsgListMap1.put("role", 0);

        Map<String, Object> auditMsgListMap2 = new LinkedHashMap<String, Object>();
        auditMsgListMap2.put("auditMsgId", 235);
        auditMsgListMap2.put("auditTitle", "羽毛球协会");
        auditMsgListMap2.put("registerName", "羽毛球协会");
        auditMsgListMap2.put("registerTime", new Date());
        auditMsgListMap2.put("auditState", 2);
        auditMsgListMap2.put("role", 0);

        //创建放全部审核信息的list集合，并将它放入返回数据
        List<Map<String, Object>> auditMsgList = new ArrayList<Map<String, Object>>();
        auditMsgList.add(auditMsgListMap1);
        auditMsgList.add(auditMsgListMap2);
        returnData.setData(auditMsgList);
        return returnData.getMap();
    }

    /**
     * 根据某个审核消息id查看审核信息的具体内容
     * @param auditMsgId 审核信息的id
     * @param session    会话
     * @return 审核信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/sau/audit/join/{auditMsgId}/{role}", method = RequestMethod.GET)
    public Map<String, Object> getAuditMsgDetail(@PathVariable("auditMsgId") int auditMsgId, @PathVariable(value = "role") int role, HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();
        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }
        if (auditMsgId > 0) {
            //根据审核id和用户id,和role值从dao中得到审核消息内容
            // TODO: 2017/8/27
            out.println("审核id是：" + auditMsgId + "。用户id是：" + userId + "要审核的消息的是什么角色的："+role);

            //审核社团注册信息
            if (role == 0) {
                //模拟数据
                //创建封装某个审核详细消息的map对象
                Map<String, Object> auditMsgDetailMap = new LinkedHashMap<String, Object>();
                auditMsgDetailMap.put("auditMsgId", auditMsgId);
                auditMsgDetailMap.put("realName", "张三");
                auditMsgDetailMap.put("registerTime", new Date());
                auditMsgDetailMap.put("email", "s19961234@126.com");
                auditMsgDetailMap.put("phone", "18888888888");
                auditMsgDetailMap.put("clubName", "乒乓球协会");
                auditMsgDetailMap.put("clubType", "体育类");
                auditMsgDetailMap.put("description", "这是乒乓球协会的描述");
                auditMsgDetailMap.put("file", "申请文件.doc");

                //将map集合数据放入到返回数据中，返回
                returnData.setData(auditMsgDetailMap);

                //审核会员注册信息
            } else if (role == 1) {
                //模拟数据
                //创建封装某个审核详细消息的map对象
                Map<String, Object> auditMsgDetailMap = new LinkedHashMap<String, Object>();
                auditMsgDetailMap.put("auditMsgId", auditMsgId);
                auditMsgDetailMap.put("userName", "s19961234@126.com");
                auditMsgDetailMap.put("registerTime", new Date());
                auditMsgDetailMap.put("personLogo", "a.jpg");
                auditMsgDetailMap.put("realName", "张三");
                auditMsgDetailMap.put("studentId", "151611222");
                auditMsgDetailMap.put("gender", "男");
                auditMsgDetailMap.put("birthday", new Date());
                auditMsgDetailMap.put("email", "s19961234@126.com");
                auditMsgDetailMap.put("departmentName", "金融学院与统计学院");
                auditMsgDetailMap.put("majorName", "信息与计算科学");
                auditMsgDetailMap.put("address", "8#110");

                //将map集合数据放入到返回数据中，返回
                returnData.setData(auditMsgDetailMap);
            }else{
                returnData.setStateCode(ResponseCode.REQUEST_ERROR,"输入的role值不合法，请重新输入。");
                return returnData.getMap();
            }
        } else {
            returnData.setStateCode(ResponseCode.REQUEST_ERROR, "要查询的审核消息的id不符合条件，请重新查询");
            return returnData.getMap();
        }
        return returnData.getMap();
    }

    /**
     * 发送审核结果，得到审核结果
     * @param auditMsgId 审核消息id
     * @param resultMap 结果的map集合
     * @param session 会话
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/sau/audit/reg/{auditMsgId}", method = RequestMethod.PUT)
    public Map<String, Object> sendAuditMsgResult(@PathVariable("auditMsgId") int auditMsgId,@RequestParam Map<String,Object> resultMap, HttpSession session)  {
        BaseReturnData returnData = new BaseReturnData();
        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }
        //如果接受到审核结果为空
        if(resultMap == null){
            returnData.setStateCode(ResponseCode.REQUEST_ERROR,"还没有发送审核结果，请重新操作");
            return returnData.getMap();
        }
        //从map中得到审核结果和审核状态
        int auditState = Integer.parseInt(resultMap.get("auditState").toString());
        String auditResult = (String) resultMap.get("auditResult");
        //对审核状态码进行判断
        if(auditState>=2 || auditState<0){
            returnData.setStateCode(ResponseCode.REQUEST_ERROR,"审核状态码不正确，请重新输入。");
            return returnData.getMap();
        }
        if(auditMsgId>0){
            //根据用户id，审核消息id，通过dao将审核结果和状态存入数据库
            // TODO: 2017/8/27
            out.println("用户id和审核消息id"+userId+","+auditMsgId+"。审核结果和状态为："+auditState+","+auditResult);
        }else{
            returnData.setStateCode(ResponseCode.REQUEST_ERROR,"审核消息的id不正确，请重新执行操作");
            return returnData.getMap();
        }
        return returnData.getMap();
    }

    /**
     * 在线预览审核文件，
     * @param auditMsgId 审核消息id
     * @param file 审核文件名称
     * @param session 会话
     * @param response 响应
     * html文件的输出流，直接输出到浏览器
     */
    @RequestMapping(value = "/sau/audit/reg/{auditMsgId}/file/online", method = RequestMethod.GET)
    public void openOnlineFile(@PathVariable("auditMsgId") int auditMsgId, @RequestParam(required = false) String file, HttpSession session, HttpServletResponse response) {
        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }
        //根据用户id和审核消息id，将从dao中取出审核文件的文件名
        // TODO: 2017/8/27
        out.println("用户id和审核id："+userId+","+auditMsgId);

        //将word文件转化为html，然后输出html输出流
        // TODO: 2017/8/28
        //模拟数据
        String fileNameHtml = "1.html";
        //设置上传目录,即转化为html的目录
        String uploadPath = WebPath.rootPath+"//WEB-INF//upload//clubRegister";
        try {
            InputStream in = new FileInputStream(new File(uploadPath,fileNameHtml));
            //得到输出流
            OutputStream out = response.getOutputStream();
            //输出文件
            byte[] byteBuffer = new byte[1024];
            int len = 0;
            while ( (len=in.read(byteBuffer)) != -1 ){
                out.write(byteBuffer,0,len);
            }
            //关闭流
            out.close();
            in.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
            throw new RuntimeException("找不到文件");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("找不到文件");
        }

    }

    /**
     * 下载某个审核消息的审核文件，向浏览器输出下载信息
     * @param auditMsgId 审核消息id
     * @param fileName 文件名
     * @param session 会话
     * @param response 响应
     */
    @RequestMapping(value = "/sau/audit/reg/{auditMsgId}/file",method = RequestMethod.GET)
    public void downFile(@PathVariable int auditMsgId, @RequestParam(value = "file",required = false) String fileName, HttpSession session,HttpServletResponse response){
        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }
        //根据用户id和审核消息id，将从dao中取出审核文件的文件名
        // TODO: 2017/8/27
        out.println("用户id和审核id："+userId+","+auditMsgId);

        //模拟数据
        String fileNameWord = "1.doc";
        //设置上传目录,即存放审核word文档的目录
        String uploadPath = WebPath.rootPath+"//WEB-INF//upload//clubRegister";
        try {
            InputStream in = new FileInputStream(new File(uploadPath,fileNameWord));
            //设置下载的响应头
            response.setHeader("content-disposition","attachment;fileName="+fileNameWord);
            //得到输出流
            OutputStream out = response.getOutputStream();
            //输出文件
            byte[] byteBuffer = new byte[1024];
            int len = 0;
            while ( (len=in.read(byteBuffer)) != -1 ){
                out.write(byteBuffer,0,len);
            }
            //关闭流
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据查找内容查找审核消息
     * @param findContent 查找内容
     * @param session 会话
     * @return 返回审核消息列表
     */
    @ResponseBody
    @RequestMapping(value = "/sau/audit/reg/search",method = RequestMethod.GET)
    public Map<String,Object> searchAuditMsg(@RequestParam String findContent, HttpSession session){
        BaseReturnData returnData = new BaseReturnData();

        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        //如果要查找的消息的是空的话，返回全部审核消息
        if(StringUtils.isEmpty(findContent)){
            return getAllAuditMsg(session);
        }

        //根据用户id和查找内容，通过dao从数据库中查找相应的审核信息
        // TODO: 2017/8/27  
        out.println("通过dao用户id和查找内容是："+userId+","+findContent);

        //将得到的数据放入每个map集合中
        Map<String, Object> auditMsgListMap1 = new LinkedHashMap<String, Object>();
        auditMsgListMap1.put("auditMsgId", 234);
        auditMsgListMap1.put("auditTitle", "乒乓球协会"+"。查找的内容是："+findContent);
        auditMsgListMap1.put("registerName", "乒乓球协会");
        auditMsgListMap1.put("registerTime", new Date());
        auditMsgListMap1.put("auditState", 2);
        auditMsgListMap1.put("role", 0);

        Map<String, Object> auditMsgListMap2 = new LinkedHashMap<String, Object>();
        auditMsgListMap2.put("auditMsgId", 235);
        auditMsgListMap2.put("auditTitle", "羽毛球协会"+"。查找的内容是："+findContent);
        auditMsgListMap2.put("registerName", "羽毛球协会");
        auditMsgListMap2.put("registerTime", new Date());
        auditMsgListMap2.put("auditState", 2);
        auditMsgListMap2.put("role", 0);

        //创建放全部审核信息的list集合，并将它放入返回数据
        List<Map<String, Object>> auditMsgList = new ArrayList<Map<String, Object>>();
        auditMsgList.add(auditMsgListMap1);
        auditMsgList.add(auditMsgListMap2);

        returnData.setData(auditMsgList);
        return returnData.getMap();
    }















}
