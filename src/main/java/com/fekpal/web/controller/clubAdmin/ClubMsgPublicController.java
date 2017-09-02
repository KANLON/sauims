package com.fekpal.web.controller.clubAdmin;

import com.fekpal.cons.ResponseCode;
import com.fekpal.domain.controllerDomain.ClubPublishedNewMsgDomain;
import com.fekpal.tool.BaseReturnData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

import static java.lang.System.out;

/**
 * 社团的消息发布的控制类
 * Created by hasee on 2017/8/26.
 */
@Controller
public class ClubMsgPublicController {

    /**
     * 根据用户id和消息类型，返回发布对象
     * @param messageType 消息类型
     * @param session 用户会话
     * @return 发布对象即社团对象
     */
    @ResponseBody
    @RequestMapping(value = "/club/members",method = RequestMethod.GET)
    public Map<String,Object> getMassageType(@RequestParam("messageType")int messageType, HttpSession session){
        BaseReturnData returnData = new BaseReturnData();

        //得到用户id
        int userId = 0;
        if(session.getAttribute("userCode")!= null){
            userId = (Integer) session.getAttribute("userCode");
        }

        //通过用户id和消息类型从dao中返回消息类型的list数据
        // TODO: 2017/8/26

        out.println("用户id是："+userId+"。消息类型是："+messageType);

        //模拟 从dao中得到数据
        Map<String,Object> clubObjMap1 = new LinkedHashMap<String, Object>();
        clubObjMap1.put("userId",1);
        clubObjMap1.put("realName","张三");
        Map<String,Object> clubObjMap2 = new LinkedHashMap<String, Object>();
        clubObjMap2.put("userId",1);
        clubObjMap2.put("realName","李四");

        //创建存放返回发布对象的list集合
        List<Map<String,Object>> clubObjList = new ArrayList<Map<String, Object>>();
        //将每个发布对象放入的list集合中去
        clubObjList.add(clubObjMap1);
        clubObjList.add(clubObjMap2);

        //将社团对象数据放入返回数据中并返回
        returnData.setData(clubObjList);
        return returnData.getMap();
    }


    /**
     * 根据用户id返回该用户之前发布的消息
     * @param session 用户会话
     * @return 历史发布消息信息
     */
    @ResponseBody
    @RequestMapping(value = "/club/msg/old",method = RequestMethod.GET)
    public Map<String,Object> getAllOldMsg(HttpSession session){
        BaseReturnData returnData = new BaseReturnData();
        //得到用户id
        int userId = 0;
        if(session.getAttribute("userCode")!= null){
            userId = (Integer) session.getAttribute("userCode");
        }

        //根据用户id从dao中返回该用户之前所发布的全部消息
        // TODO: 2017/8/26

        //模拟得到了数据
        //用来装某条信息的map集合
        Map<String,Object> msgMap1 = new LinkedHashMap<String, Object>();
        msgMap1.put("messageId",101);
        msgMap1.put("messageTitle","乒乓球活动");
        msgMap1.put("sendTime",new Date());
        msgMap1.put("messageType",2);
        //用来装某条信息的map集合
        Map<String,Object> msgMap2 = new LinkedHashMap<String, Object>();
        msgMap2.put("messageId",102);
        msgMap2.put("messageTitle","乒乓球比赛");
        msgMap2.put("sendTime",new Date());
        msgMap2.put("messageType",0);

        //创建消息列表的list集合，并将消息装入list集合，并返回
        List<Map<String,Object>> msgList  = new ArrayList<Map<String, Object>>();
        msgList.add(msgMap1);
        msgList.add(msgMap2);
        returnData.setData(msgList);
        return returnData.getMap();
    }

    /**
     * 根据历史发布消息id返回消息详细内容的方法
     * @param messageId 消息id
     * @param session 会话
     * @return 某条历史发布消息的详细信息
     */
    @ResponseBody
    @RequestMapping(value = "/club/msg/old/{messageId}",method = RequestMethod.GET)
    public Map<String,Object> getOneOldMsg (@PathVariable int messageId,HttpSession session){
        BaseReturnData returnData = new BaseReturnData();

        //得到用户id
        int userId = 0;
        if(session.getAttribute("userCode")!= null){
            userId = (Integer) session.getAttribute("userCode");
        }

        //根据用户id和消息id从dao中获取得到消息对象，并从该对象获取属性出来
        // TODO: 2017/8/26

        //模拟数据
        String messageTitle = "乒乓球比赛";
        String messageContent = "在星期天，会有一场乒乓球比赛";
        //后端要精确的秒
        Date sendTime = new Date();
        int messageType = 2;

        //创建发布对象的map集合
        Map<String,Object> publishedObjMap1 = new LinkedHashMap<String, Object>();
        publishedObjMap1.put("userId",1);
        publishedObjMap1.put("realName","张三");

        //创建发布对象的map集合
        Map<String,Object> publishedObjMap2 = new LinkedHashMap<String, Object>();
        publishedObjMap2.put("userId",2);
        publishedObjMap2.put("realName","李四");

        //创建发布对象的list集合，并将每个map集合的发布对象放入list中
        List<Map<String,Object>> publishedObjList = new ArrayList<Map<String, Object>>();
        publishedObjList.add(publishedObjMap1);
        publishedObjList.add(publishedObjMap2);

        //将某条信息的map集合放入返回数据中
        Map<String,Object> msgMap = new LinkedHashMap<String, Object>();
        msgMap.put("messageId",messageId);
        msgMap.put("messageTitle",messageTitle);
        msgMap.put("messageContent",messageContent);
        msgMap.put("sendTime",sendTime);
        msgMap.put("messageType",messageType);
        msgMap.put("publishedObject",publishedObjList);
        returnData.setData(msgMap);

        return returnData.getMap();
    }

    /**
     * 社团发布消息给全部成员
     * @param newMsg 消息对象
     * @param session 会话
     * @return 是否发送成功
     */
    @ResponseBody
    @RequestMapping(value = "/club/msg/new/all",method = RequestMethod.POST)
    public Map<String,Object> sendMsgToAll (@RequestBody ClubPublishedNewMsgDomain newMsg, HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();

        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }
        //如果前端传过来的发布对象不是空
        if(newMsg!=null){
            //从发布消息对象中得到相应的值
            String msgTitle = newMsg.getMessageTitle();
            String msgContent = newMsg.getMessageContent();
            Date sendTime = newMsg.getSendTime();
            List<Map<String,Integer>> publishObject = newMsg.getPublishedObject();

            //根据用户id将消息发布给相应的用户
            // TODO: 2017/8/26  
            out.println("将该消息存入数据库（发送给所有人的）："+newMsg);
            
        }else {
            returnData.setStateCode(ResponseCode.REQUEST_ERROR,"所要发布的消息内容为空，不能发布");
        }
        return returnData.getMap();
    }

    /**
     * 发送消息个给本社团内的人
     * @param newMsg 消息对象
     * @param session 会话
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/club/msg/new/group",method = RequestMethod.POST)
    public Map<String,Object> sendMsgToGroup (@RequestBody ClubPublishedNewMsgDomain newMsg,HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();

        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        if(newMsg!=null){
            //从发布消息对象中得到相应的值
            String msgTitle = newMsg.getMessageTitle();
            String msgContent = newMsg.getMessageContent();
            Date sendTime = newMsg.getSendTime();
            List<Map<String,Integer>> publishObject = newMsg.getPublishedObject();

            //根据用户id将消息发布给相应的用户
            // TODO: 2017/8/26
            out.println("将该消息存入数据库（发送给本社团内的人）："+newMsg);

        }else {
            returnData.setStateCode(ResponseCode.REQUEST_ERROR,"所要发布的消息内容为空，不能发布");
        }

        return returnData.getMap();
    }

    /**
     * 发送消息给自定义对象
     * @param newMsg 消息对象
     * @param session 会话
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/club/msg/new/person",method = RequestMethod.POST)
    public Map<String,Object> sendMsgToPerson (@RequestBody ClubPublishedNewMsgDomain newMsg,HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();
        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        if(newMsg!=null){
            //从发布消息对象中得到相应的值
            String msgTitle = (String) newMsg.getMessageTitle();
            String msgContent = (String) newMsg.getMessageContent();
            Date sendTime = (Date) newMsg.getSendTime();
            List<Map<String,Integer>> publishObject = newMsg.getPublishedObject();
            //创建用来放发布对象的id的list集合
            List<Integer> userIdList = new ArrayList<Integer>();
            if(publishObject!=null) {
                //遍历前端发过来的发布id的list集合，从map集合中得到用户id，并将它放入到id的list集合中
                for (Map<String, Integer> userIdMap : publishObject) {
                    userIdList.add(userIdMap.get("userId"));
                }
            }

            //根据用户id将消息发布给相应的用户
            // TODO: 2017/8/26
            out.println("msgTiTle:"+msgTitle);
            out.println("msgContent:"+msgContent);
            out.println("sendTime:"+sendTime);
            out.println("userIdList:"+userIdList);

            out.println("将该消息存入数据库（发送给指定的用户（社团）的）："+newMsg+"用户id的list集合为："+userIdList);
        }else {
            returnData.setStateCode(ResponseCode.REQUEST_ERROR,"所要发布的消息内容为空，不能发布");
        }

        return returnData.getMap();
    }

    /**
     * 根据消息id删除历史发布消息
     * @param deleteMsgIdMap 要删除的消息的id
     * @param session 会话
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/club/msg/old",method = RequestMethod.DELETE)
    public Map<String,Object> deleteMsgs (@RequestParam Map<String,Object> deleteMsgIdMap,HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();

        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        if(deleteMsgIdMap!=null){
            //从要删除的消息id的map集合中得到要删除的消息id，并将它们放入list集合中
            // TODO: 2017/8/26

            //初始化装要删除消息id的list集合
            List<Integer> deleteMsgIdList = new ArrayList<Integer>();
            for(int i =0;i<deleteMsgIdMap.size()-1;i++) {
                //组装map集合的键
                String keyId = "deleteMsgIds" +"["+i+"]"+ "[" + "messageId" + "]";
                //将得到的msgId值放入list集合
                deleteMsgIdList.add( Integer.parseInt(deleteMsgIdMap.get(keyId).toString()));
            }
            //根据userId和消息id在数据库中做相应删除操作
            out.println("用户id为："+userId+"。要删除的id是:(list)"+ deleteMsgIdList);
        }else{
            returnData.setStateCode(ResponseCode.REQUEST_ERROR,"没有选择要删除的id，请重新选择。");
        }
        return returnData.getMap();
    }

    /**
     *根据查找内容查找历史发布消息
     * @param findContent 查找内容
     * @param session 会话
     * @return 返回消息列表
     */
    @ResponseBody
    @RequestMapping(value = "/club/msg/old/search",method = RequestMethod.GET)
    public Map<String,Object> searchMsg (@RequestParam String findContent,HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();

        //得到用户id
        int userId = 0;
        if (session.getAttribute("userCode") != null) {
            userId = (Integer) session.getAttribute("userCode");
        }

        if(findContent!=null){
            //根据查找内容和用户id，从dao中获得消息对象，并从消息对象中提取属性，构成消息列表，返回
            // TODO: 2017/8/26
            out.println("根据查找内容和用户id，从dao中获得消息对象，并从消息对象中提取属性，构成消息列表，返回。查找内容："+findContent+"。用户id"+userId);

            //模拟得到了数据
            //用来装某条信息的map集合
            Map<String,Object> msgMap1 = new LinkedHashMap<String, Object>();
            msgMap1.put("messageId",101);
            msgMap1.put("messageTitle","乒乓球活动"+"。查找内容是;"+findContent);
            msgMap1.put("sendTime",new Date());
            msgMap1.put("messageType",2);
            //用来装某条信息的map集合
            Map<String,Object> msgMap2 = new LinkedHashMap<String, Object>();
            msgMap2.put("messageId",102);
            msgMap2.put("messageTitle","乒乓球比赛"+"。查找内容是;"+findContent);
            msgMap2.put("sendTime",new Date());
            msgMap2.put("messageType",0);

            //创建消息列表的list集合，并将消息装入list集合，并返回
            List<Map<String,Object>> msgList  = new ArrayList<Map<String, Object>>();
            msgList.add(msgMap1);
            msgList.add(msgMap2);
            returnData.setData(msgList);
        }else {
            //如果要查找的内容为空，则返回所有消息
            return getAllOldMsg(session);
        }
        return returnData.getMap();
    }


}
