package com.fekpal.web.controller.clubAdmin;

import com.fekpal.cons.ResponseCode;
import com.fekpal.tool.BaseReturnData;
import com.fekpal.tool.FileUploadTool;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

import static java.lang.System.out;

/**
 * 社团管理端的年度注册的控制类
 * Created by hasee on 2017/8/27.
 */
@Controller
public class ClubAnnRegisterController {

    /**
     * 查看全部注册的信息的方法
     *
     * @param annARegisterMap 年度审核信息
     * @param session         会话
     * @param request         请求
     * @return 全部年度注册信息
     */
    @ResponseBody
    @RequestMapping(value = "/club/ann", method = RequestMethod.GET)
    public Map<String, Object> getAllAuditMsg(@RequestParam(required = false) Map<String, Object> annARegisterMap, HttpSession session, HttpServletRequest request) {
        BaseReturnData returnData = new BaseReturnData();

        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        //从dao中根据用户id得到本社社团历史年度注册信息的对象，并获取该对象属性
        // TODO: 2017/8/27
        out.println("从dao中根据用户id得到社团年度审核信息的对象，并获取该对象属性,用户id为：" + userId);
        //模拟数据
        //将得到的数据放入每个map集合中
        Map<String, Object> auditMsgListMap1 = new LinkedHashMap<String, Object>();
        auditMsgListMap1.put("auditMsgId", 234);
        auditMsgListMap1.put("auditTitle", "2017年");
        auditMsgListMap1.put("submitTime", new Date());
        auditMsgListMap1.put("auditState", 2);

        Map<String, Object> auditMsgListMap2 = new LinkedHashMap<String, Object>();
        auditMsgListMap2.put("auditMsgId", 235);
        auditMsgListMap2.put("auditTitle", "2016年");
        auditMsgListMap2.put("submitTime", new Date());
        auditMsgListMap2.put("auditState", 2);

        //创建放全部年度注册信息的list集合，并将它放入返回数据
        List<Map<String, Object>> auditMsgList = new ArrayList<Map<String, Object>>();
        auditMsgList.add(auditMsgListMap1);
        auditMsgList.add(auditMsgListMap2);
        returnData.setData(auditMsgList);
        return returnData.getMap();
    }

    /**
     * 根据某个年度注册消息id查看年度审核信息的具体内容
     *
     * @param auditMsgId 注册信息的id
     * @param session    会话
     * @return 注册信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/club/ann/{auditMsgId}", method = RequestMethod.GET)
    public Map<String, Object> getAuditMsgDetail(@PathVariable("auditMsgId") int auditMsgId, HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();
        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }
        if (auditMsgId > 0) {
            //根据审核id和用户id从dao中得到年度注册消息内容
            // TODO: 2017/8/27
            out.println("从数据库中得到对象年度审核信息id是：" + auditMsgId + "。用户id是：" + userId);

            //该社团年度注册信息
            //模拟数据
            //创建封装某个年度注册消息的map对象
            Map<String, Object> auditMsgDetailMap = new LinkedHashMap<String, Object>();
            auditMsgDetailMap.put("auditMsgId", auditMsgId);
            auditMsgDetailMap.put("submitTime", new Date());
            auditMsgDetailMap.put("description", "这是乒乓球协会的描述");
            auditMsgDetailMap.put("fileName", "a.doc");
            auditMsgDetailMap.put("auditState", 0);
            auditMsgDetailMap.put("auditResult", "");

            //将map集合数据放入到返回数据中，返回
            returnData.setData(auditMsgDetailMap);
        } else {
            returnData.setStateCode(ResponseCode.REQUEST_ERROR, "要查询的年度注册消息的id不符合条件，请重新查询");
            return returnData.getMap();
        }
        return returnData.getMap();
    }

    /**
     * 根据查找内容查找年度审核消息
     *
     * @param findContent 查找内容
     * @param session     会话
     * @return 返回审核消息列表
     */
    @ResponseBody
    @RequestMapping(value = "/club/ann/search", method = RequestMethod.GET)
    public Map<String, Object> searchAuditMsg(@RequestParam String findContent, HttpSession session, HttpServletRequest request) {
        BaseReturnData returnData = new BaseReturnData();

        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        //如果要查找的消息的是空的话，返回全部审核消息
        if (StringUtils.isEmpty(findContent)) {
            return getAllAuditMsg(null, session, request);
        }

        //根据用户id和查找内容，通过dao从数据库中查找相应的年度注册信息
        // TODO: 2017/8/27
        out.println("通过dao用户id和查找内容是：" + userId + "," + findContent);

        //将得到的数据放入每个map集合中
        Map<String, Object> auditMsgListMap1 = new LinkedHashMap<String, Object>();
        auditMsgListMap1.put("auditMsgId", 1);
        auditMsgListMap1.put("auditTitle", "2017年" + "。查找的内容是：" + findContent);
        auditMsgListMap1.put("registerName", "张三");
        auditMsgListMap1.put("registerTime", new Date());
        auditMsgListMap1.put("auditState", 2);

        Map<String, Object> auditMsgListMap2 = new LinkedHashMap<String, Object>();
        auditMsgListMap2.put("auditMsgId", 2);
        auditMsgListMap2.put("auditTitle", "2016" + "。查找的内容是：" + findContent);
        auditMsgListMap2.put("registerTime", new Date());
        auditMsgListMap2.put("auditState", 2);

        //创建放全部审核信息的list集合，并将它放入返回数据
        List<Map<String, Object>> auditMsgList = new ArrayList<Map<String, Object>>();
        auditMsgList.add(auditMsgListMap1);
        auditMsgList.add(auditMsgListMap2);

        returnData.setData(auditMsgList);
        return returnData.getMap();
    }

    // TODO: 2017/9/2

    /**
     * 社团管理端提交年度注册信息
     * 审核文件放在D://masterspring//MySAUImages//clubAnnRegister
     * <p>
     * 结果的map集合
     *
     * @param session 会话
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/club/ann/one", method = RequestMethod.POST)
    public Map<String, Object> submitRegisterMsg(@RequestParam MultipartFile[] file, @RequestParam Map<String, Object> clubMsgMap, HttpServletRequest request, HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();

        //如果接受到审核结果为空
        if (clubMsgMap == null) {
            returnData.setStateCode(ResponseCode.REQUEST_ERROR, "还没有发送审核结果，请重新操作");
            return returnData.getMap();
        }
        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        //在service层验证描述，提交的文件的安全性等
        // TODO: 2017/9/2

        //如果上传的文件不符合符合大小，文件类型等
        if ((Integer) (handleFile(file).get("code")) != 0) {
            //如果上传的文件不符合条件，返回相应内容
            return handleFile(file);
        }

        //初始化文件名，描述，提交时间
        String fileName = "";
        String description = "";
        Date submitTime = new Date();

        //将文件存入服务器中的与本项目同目录的//MySAUImages/clubAnnRegister文件夹中，返回文件名
        List<String> fileNameList = FileUploadTool.fileHandle(file, request, "clubAnnRegister");
        if( fileNameList!= null) {
            fileName = fileNameList.get(0);
        }else {
            returnData.setStateCode(ResponseCode.REQUEST_ERROR,"还没有发送文件过来，请重新发送");
            return returnData.getMap();
        }

        //从发来的map集合中得到年度注册描述
        if (clubMsgMap.get("description") != null) {
            description = clubMsgMap.get("description").toString();
        }

        //根据用户id，将本社团的年度注册信息，描述，文件名，和提交时间存入数据库
        // TODO: 2017/8/27
        out.println("用户id：" + userId + ".;描述，和文件名,时间等" + fileName + "," + description + "," + submitTime);

        return returnData.getMap();
    }

    /**
     * 上传文件的方法
     *
     * @param request   请求
     * @param childPath 子目录
     */
    public void uploadFile(HttpServletRequest request, String childPath) {
        try {
            // 1. 文件上传工厂
            FileItemFactory factory = new DiskFileItemFactory();
            // 2. 创建文件上传核心工具类
            ServletFileUpload upload = new ServletFileUpload(factory);

            // 一、设置单个文件允许的最大的大小： 10M
            upload.setFileSizeMax(10 * 1024 * 1024);
            // 二、设置文件上传表单允许的总大小: 30M
            upload.setSizeMax(30 * 1024 * 1024);
            // 三、 设置上传表单文件名的编码
            // 相当于：request.setCharacterEncoding("UTF-8");
            upload.setHeaderEncoding("UTF-8");

            out.println(1);
            // 3. 判断： 当前表单是否为文件上传表单
            if (upload.isMultipartContent(request)) {
                // 4. 把请求数据转换为一个个FileItem对象，再用集合封装
                out.println(2);

                List<FileItem> list = upload.parseRequest(request);
                out.println(list);
                // 遍历： 得到每一个上传的数据
                for (FileItem item : list) {
                    // 判断：普通文本数据
                    out.println(3);

                    if (item.isFormField()) {
                        out.println(5);

                        // 普通文本数据
                        String fieldName = item.getFieldName();    // 表单元素名称
                        String content = item.getString();        // 表单元素名称， 对应的数据
                        //item.getString("UTF-8");  指定编码
                        out.println(fieldName + " " + content);
                    }
                    // 上传文件(文件流) ----> 上传到upload目录下
                    else {
                        out.println(6);

                        // 普通文本数据
                        String fieldName = item.getFieldName();    // 表单元素名称
                        String name = item.getName();            // 文件名
                        String content = item.getString();        // 表单元素名称， 对应的数据
                        String type = item.getContentType();    // 文件类型
                        InputStream in = item.getInputStream(); // 上传文件流

//						 *  四、文件名重名
//						 *  对于不同用户readme.txt文件，不希望覆盖！
//						 *  后台处理： 给用户添加一个唯一标记!

                        // a. 随机生成一个唯一标记
                        String id = UUID.randomUUID().toString();
                        // b. 与文件名拼接
                        name = id + "#" + name;

                        // 获取上传基路径
                        out.println(request.getContextPath());
                        out.println(request.getServletPath());
                        String path = request.getContextPath() + "//" + childPath;
                        // 创建目标文件
                        File file = new File(path, name);

                        // 工具类，文件上传
                        item.write(file);
                        item.delete();   //删除系统产生的临时文件

                        out.println();
                    }

                }
            } else {
                out.println("当前表单不是文件上传表单，处理失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * springmvc的上传方法
     *
     * @param request   请求
     * @param childPath 子目录
     */
    public void springUploadFile(HttpServletRequest request, String childPath) {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    out.println(request.getContextPath());
                    String path = request.getContextPath() + "//" + file.getOriginalFilename();
                    //上传
                    try {
                        file.transferTo(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }

            }

        }
    }

    /**
     * 处理上传文件的方法
     *
     * @param myfiles 上传的文件
     * @return 上传信息是否正确
     */
    public Map<String, Object> handleFile(MultipartFile[] myfiles) {
        BaseReturnData returnData = new BaseReturnData();
        //判断文件格式和大小是否符合
        for (MultipartFile myfile : myfiles) {
            if (!myfile.isEmpty()) {
                if (myfile.getSize() > 1024 * 1024 * 10) {
                    returnData.setStateCode(ResponseCode.REQUEST_ERROR, "文件大于10m请重新上传");
                    return returnData.getMap();
                }
                if (!myfile.getContentType().toString().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                        && !myfile.getContentType().toString().equals("application/msword")) {
                    returnData.setStateCode(ResponseCode.REQUEST_ERROR, "上传的文件不符合格式，请重新上传");
                    return returnData.getMap();
                }
            } else {
                returnData.setStateCode(1, "没上传文件，请重新上传");
                return returnData.getMap();
            }
        }
        //如果上传的文件为空，返回提示语句
        if (myfiles == null) {
            returnData.setStateCode(1, "没上传文件，请重新上传");
        }
        return returnData.getMap();
    }
}
