package com.fekpal.web.controller.sauAdmin;

import com.fekpal.cons.ReadFlagCode;
import com.fekpal.cons.ResponseCode;
import com.fekpal.domain.controllerDomain.ClubDetail;
import com.fekpal.domain.controllerDomain.ClubListMsg;
import com.fekpal.domain.controllerDomain.NewMsgListDomain;
import com.fekpal.tool.BaseReturnData;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static java.lang.System.out;

/**
 * 校社联的社团信息的控制类
 * Created by hasee on 2017/8/23.
 */
@Controller
public class SauClubMsgController {

    /**
     * 返回社团信息列表的方法
     * @param session 会话
     * @return 社团信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/sau/club",method = RequestMethod.GET)
    public Map<String,Object> getAllClubMsg(HttpSession session){
        BaseReturnData returnData = new BaseReturnData();
        //得到用户id
        int userId = 0;
        if(session.getAttribute("userCode")!= null){
            userId = (Integer) session.getAttribute("userCode");
        }

        //根据用户id查询要返回的社团信息的对象
        // TODO: 2017/8/23
        out.println("用户id为:"+userId+"。这里要查询社团信息，返回社团对象");

        //将得到的对象，从里面循环获取值赋值到返回前端的社团列表对象中
        //初始化返回对象集合
        List<Map<String,Object>> clubMsgList = new ArrayList<Map<String, Object>>();

        //模拟
        Map<String,Object> clubMsgMap1  = new LinkedHashMap<String, Object>();
        clubMsgMap1.put("clubId",123);
        clubMsgMap1.put("clubName","乒乓球协会");
        clubMsgMap1.put("members",100);
        clubMsgMap1.put("likeNumber",20);
        Map<String,Object> clubMsgMap2  = new LinkedHashMap<String, Object>();
        clubMsgMap2.put("clubId",124);
        clubMsgMap2.put("clubName","羽毛球协会");
        clubMsgMap2.put("members",100);
        clubMsgMap2.put("likeNumber",20);

        //将对象放入返回json数据中
        clubMsgList.add(clubMsgMap1);
        clubMsgList.add(clubMsgMap2);
        returnData.setData(clubMsgList);

        return returnData.getMap();
    }


    /**
     * 得到某个社团的详细信息
     * @param session 会话
     * @param clubId 社团ID
     * @return 社团详细信息
     */
    @ResponseBody
    @RequestMapping(value = "/sau/club/{clubId}",method = RequestMethod.GET)
    public Map<String,Object> getOneClubMsg(HttpSession session, @PathVariable int clubId){
        BaseReturnData returnData = new BaseReturnData();
        //如果要查询的社团id是小于或等于0的话，返回错误
        if(clubId<=0){
            returnData.setStateCode(ResponseCode.REQUEST_ERROR,"你所要查询的社团id是空的，请重新点击查询");
            return returnData.getMap();
        }

        //得到用户id
        int userId = 0;
        if(session.getAttribute("userCode")!= null){
            userId = (Integer) session.getAttribute("userCode");
        }
        
        //根据用户Id和社团id通过dao从社团表里面返回社团对象
        // TODO: 2017/8/23

        //将对象的属性赋值到显示给前端的clubDetail对象中
        ClubDetail clubDetail = new ClubDetail();
        clubDetail.setClubId(clubId);
        clubDetail.setAdminName("张三");
        clubDetail.setClubLogo("a.jpg");
        clubDetail.setClubName("乒乓球协会");
        clubDetail.setDescription("我们是一个乒乓球爱好者的集合");
        clubDetail.setEmail("s19961234@126.com");
        clubDetail.setFoundTime(new Date());
        clubDetail.setMembers(100);
        clubDetail.setPhone("18316821383");

        //将对象加入的到返回数据中
        returnData.setData(clubDetail);
        return returnData.getMap();
    }

    /**
     * 根据查找内容查找某个社团
     * @param request 请求
     * @param session 会话
     * @param findContent 查找内容
     * @return 查找的社团的列表对象信息
     */
    @ResponseBody
    @RequestMapping(value = "/sau/club/search",method = RequestMethod.GET)
    public Map<String,Object> searchMsg(HttpServletRequest request, HttpSession session, @RequestParam String findContent){
        BaseReturnData returnData = new BaseReturnData();

        //得到用户的id
        int userId = 0;
        if(session.getAttribute("userCode")!=null){
            userId = (Integer) session.getAttribute("userCode");
        }

        //如果查找内容为空的话，则查询全部社团
        if(StringUtils.isEmpty(findContent.trim())){
            return getAllClubMsg(session);
        }

        //根据用户id和查找内容，从数据库中查找相关社团，只要查找社团名就可以了,得到相关社团列表的对象
        out.println("用户id"+userId+"。要查找的内容："+findContent);

        //将得到的对象，从里面循环获取值赋值到返回前端的社团列表对象中
        //初始化返回对象集合
        List<Map<String,Object>> clubMsgList = new ArrayList<Map<String, Object>>();

        //模拟
        Map<String,Object> clubMsgMap1  = new LinkedHashMap<String, Object>();
        clubMsgMap1.put("clubId",123);
        clubMsgMap1.put("clubName","乒乓球协会"+"--查找内容是："+findContent);
        clubMsgMap1.put("members",100);
        clubMsgMap1.put("likeNumber",20);
        Map<String,Object> clubMsgMap2  = new LinkedHashMap<String, Object>();
        clubMsgMap2.put("clubId",124);
        clubMsgMap2.put("clubName","羽毛球协会"+"--查找内容是："+findContent);
        clubMsgMap2.put("members",100);
        clubMsgMap2.put("likeNumber",20);

        //将对象放入返回json数据中
        clubMsgList.add(clubMsgMap1);
        clubMsgList.add(clubMsgMap2);
        returnData.setData(clubMsgList);

        return returnData.getMap();
    }


}
