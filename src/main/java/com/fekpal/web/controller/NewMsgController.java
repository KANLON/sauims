package com.fekpal.web.controller;

import com.fekpal.cons.ReadFlagCode;
import com.fekpal.cons.ResponseCode;
import com.fekpal.domain.controllerDomain.NewMsgListDomain;
import com.fekpal.tool.BaseReturnData;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static java.lang.System.out;

/**
 * 新消息的控制类
 * Created by hasee on 2017/8/22.
 */
@Controller
public class NewMsgController {

    /**
     * 根据用户id返回全部消息或者删除某些消息的方法
     * @param session 会话
     * @param request 请求
     * @param msgIdMap 消息的要删除的消息idMap集合
     * @return 消息列表内容或者删除成功的标记
     */
    @ResponseBody
    @RequestMapping(value = "/msg",method = {RequestMethod.GET,RequestMethod.DELETE})
    public Map<String,Object> getAllMsg(HttpSession session, HttpServletRequest request, @RequestParam(required = false)Map<String,Object> msgIdMap){
        //初始化返回集合
        BaseReturnData returnData = new BaseReturnData();

        int userId = 0;

        //判断用户是否已经登陆，并且得到了userCode
        if (StringUtils.isEmpty(session.getAttribute("userCode"))) {
            out.println("用户没有登陆");
            returnData.setStateCode(ResponseCode.REQUEST_ERROR, "用户还没有登陆");
            return returnData.getMap();
        } else {
            //得到用户
            userId = (Integer) session.getAttribute("userCode");
        }

        //如果请求是get，获得全部消息
        if("GET".equals(request.getMethod())) {
            returnData = new BaseReturnData();
            //初始化用户id和返回的消息列表对象
            List<NewMsgListDomain> list = new ArrayList<NewMsgListDomain>();

            //通过dao根据用户id查询得到用户的消息对象
            // TODO: 2017/8/22

            //如果得到的对象为空，直接返回
            if (false) {
                return returnData.getMap();
            }

            //模拟将消息对象中的某些属性提取出来，放入返回数据的list集合中
            NewMsgListDomain newMsgListDomain1 = new NewMsgListDomain();
            newMsgListDomain1.setMessageId(userId);
            newMsgListDomain1.setMessageTitle("乒乓球协会的活动");
            newMsgListDomain1.setReadFlag(ReadFlagCode.HAVEREAD);
            newMsgListDomain1.setSendName("张三");
            newMsgListDomain1.setSendTime(new Date());

            NewMsgListDomain newMsgListDomain2 = new NewMsgListDomain();
            newMsgListDomain2.setMessageId(userId + 1);
            newMsgListDomain2.setMessageTitle("羽毛球协会的活动");
            newMsgListDomain2.setReadFlag(ReadFlagCode.HAVEREAD);
            newMsgListDomain2.setSendName("李四");
            newMsgListDomain2.setSendTime(new Date());

            list.add(newMsgListDomain1);
            list.add(newMsgListDomain2);

            returnData.setData(list);
            return returnData.getMap();

            //如果请求是delete，删除某些消息
        }else if("DELETE".equals(request.getMethod())){
            //初始化装要删除消息id的list集合
            List<Integer> deleteMsgIdList = new ArrayList<Integer>();
            for(int i =0;i<msgIdMap.size()-1;i++) {
                //组装map集合的键
                String keyId = "deleteMsgIds" +"["+i+"]"+ "[" + "deleteMsgId" + "]";
                //将得到的msgId值放入list集合
                deleteMsgIdList.add( Integer.parseInt(msgIdMap.get(keyId).toString()));
            }
            //根据userId和消息id在数据库中做相应删除操作
            out.println("用户id为："+userId+"。要删除的id是:(list)"+ deleteMsgIdList);
        }
        return returnData.getMap();
    }

    /**
     * 根据用户ID和消息id返回某条消息的详细内容信息
     * @param messageId 消息id
     * @param session 会话
     * @return 某条消息的详细内容
     */
    @ResponseBody
    @RequestMapping(value = "/msg/{messageId}",method = RequestMethod.GET)
    public Map<String,Object> getMsgById(@PathVariable int messageId,HttpSession session){
        BaseReturnData returnData = new BaseReturnData();

        //初始化用户id
        int userId = 0;
        //用来装具体消息细节的map集合
        Map<String,Object> msgDetailMap = new LinkedHashMap<String, Object>();

        //判断用户是否已经登陆
        if(session.getAttribute("userCode")==null){
            out.println("用户还没有登陆");
            returnData.setStateCode(ResponseCode.REQUEST_ERROR,"用户还没有登陆");
            return returnData.getMap();
        }else{
            userId = (Integer) session.getAttribute("userCode");
        }

        //根据用户id和消息id从从dao中得到用户消息对象

        //将消息对象的某些属性提前出来放到返回的消息对象中
        msgDetailMap.put("messagedId",messageId);
        msgDetailMap.put("senderName","张三");
        msgDetailMap.put("sendTime",new Date());
        msgDetailMap.put("messageContent","这是某条消息的内容");

        returnData.setData(msgDetailMap);

        return returnData.getMap();
    }

    /**
     * 根据查找内容从数据库中查找相关消息
     * @param request 请求
     * @param session 会话
     * @param findContent 查找内容
     * @return 消息列表
     */
    @ResponseBody
    @RequestMapping(value = "/msg/search",method = RequestMethod.GET)
    public Map<String,Object> searchMsg(HttpServletRequest request,HttpSession session,@RequestParam String findContent){
        BaseReturnData returnData = new BaseReturnData();

        //得到用户的id
        int userId = 0;
        if(session.getAttribute("userCode")!=null){
            userId = (Integer) session.getAttribute("userCode");
        }

        //如果查找内容为空的话，则查询全部消息
        if(StringUtils.isEmpty(findContent.trim())){
           return getAllMsg(session,request,null);
        }

        //根据用户id和查找内容，从数据库中查找相关消息，只要查找标题就可以了,得到相关消息的对象
        out.println("用户id"+userId+"。要查找的内容："+findContent);

        //初始化消息列表对象
        List<NewMsgListDomain> list = new ArrayList<NewMsgListDomain>();

        //模拟从对象中获取得到相关消息列表对象
        NewMsgListDomain newMsgListDomain1 = new NewMsgListDomain();
        newMsgListDomain1.setMessageId(userId);
        newMsgListDomain1.setMessageTitle("乒乓球协会的活动"+findContent);
        newMsgListDomain1.setReadFlag(ReadFlagCode.HAVEREAD);
        newMsgListDomain1.setSendName("张三");
        newMsgListDomain1.setSendTime(new Date());

        NewMsgListDomain newMsgListDomain2 = new NewMsgListDomain();
        newMsgListDomain2.setMessageId(userId + 1);
        newMsgListDomain2.setMessageTitle("羽毛球协会的活动"+findContent);
        newMsgListDomain2.setReadFlag(ReadFlagCode.HAVEREAD);
        newMsgListDomain2.setSendName("李四");
        newMsgListDomain2.setSendTime(new Date());
        //将数据添加到返回数据中
        list.add(newMsgListDomain1);
        list.add(newMsgListDomain2);
        returnData.setData(list);

        return returnData.getMap();
    }

}
