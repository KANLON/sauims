package com.fekpal.web.controller.sau;

import com.fekpal.api.MessageSendService;
import com.fekpal.api.OrgMemberService;
import com.fekpal.api.OrgService;
import com.fekpal.api.PersonService;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import com.fekpal.common.utils.TimeUtil;
import com.fekpal.dao.model.Message;
import com.fekpal.dao.model.Org;
import com.fekpal.service.model.domain.SRMsgRecord;
import com.fekpal.web.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * 消息发布的控制类
 * @author zhangcanlong
 * @date 2018/4/5
 */
@Controller
public class SauSendPublishMsgController {

    @Autowired
    private  OrgService orgService;
    @Autowired
    private MessageSendService messageSendService;
    @Autowired
    private OrgMemberService orgMemberService;
    @Autowired
    private PersonService personService;

    private static Logger logger =   LogManager.getLogger(SauSendPublishMsgController.class);
    /**
     * 根据用户id和消息类型，返回发布对象
     * @param messageType 消息类型
     * @return 发布对象即社团对象
     */
    @ResponseBody
    @RequestMapping(value = "/sau/clubs",method = RequestMethod.GET)
    public JsonResult<List<SauMsgPublicOrgObj>> getMassageType(@RequestParam("messageType")int messageType,PageList page){
        List<Org> orgList= orgService.loadAllOrg(page.getOffset(),page.getLimit());
        Integer orgNum = orgService.countAllOrg();
        List<SauMsgPublicOrgObj> sauMsgPublicOrgObjList = new ArrayList<>() ;
        JsonResult<List<SauMsgPublicOrgObj>> result = new JsonResult<>();

        if (orgList == null || orgList.size() == 0) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "无结果");
            return result;
        }
        if(messageType == 2){
                for(Org org:orgList){
                    SauMsgPublicOrgObj obj = new SauMsgPublicOrgObj();
                   //由于发送消息的时候需要的是user_id，因此，这里选择返回用户id给前端，当做社团id
                    obj.setClubId(org.getUserId());
                    obj.setClubName(org.getOrgName());
                    sauMsgPublicOrgObjList.add(obj);
                }
        }
        result.setStateCode(ResponseCode.RESPONSE_SUCCESS,orgNum.toString());
        result.setData(sauMsgPublicOrgObjList);
        return result;
    }

    /**
     * 根据用户id返回该用户之前发布的消息
     * @param page 查询开始条数及一共的条数
     * @return 历史发布消息信息
     */
    @ResponseBody
    @RequestMapping(value = "/sau/msg/old",method = RequestMethod.GET)
    public JsonResult<List<OldPublishMsg>> getAllOldMsg(SearchPage page){
        return searchMsg(page);
    }

    /**
     * 根据历史发布消息id返回消息详细内容的方法
     * @param messageId 消息id
     * @return 某条历史发布消息的详细信息
     */
    @ResponseBody
    @RequestMapping(value = "/sau/msg/old/{messageId}",method = RequestMethod.GET)
    public JsonResult<SauMsgPublishDetail> getOneOldMsg (@PathVariable int messageId){
        Message message =  messageSendService.selectByMessageId(messageId);
        JsonResult<SauMsgPublishDetail> result = new JsonResult<>();
        if (message == null) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "无结果");
            return result;
        }
        SauMsgPublishDetail sauMsgPublishDetail = new SauMsgPublishDetail();
        sauMsgPublishDetail.setMessageId(message.getMessageId());
        sauMsgPublishDetail.setMessageContent(message.getMessageContent());
        sauMsgPublishDetail.setMessageTitle(message.getMessageTitle());
        sauMsgPublishDetail.setMessageType(message.getMessageType());
        //暂时看不了发送了给那些对象
        sauMsgPublishDetail.setPublishedObject(null);
        sauMsgPublishDetail.setSendTime(message.getReleaseTime());

        result.setCode(ResponseCode.RESPONSE_SUCCESS);
        result.setData(sauMsgPublishDetail);
        return result;
    }

    /**
     * 校社联发布消息给全部成员
     * @param newMsg 消息对象
     * @return 是否发送成功
     */
    @ResponseBody
    @RequestMapping(value = "/sau/msg/new/all",method = RequestMethod.POST)
    public JsonResult<String> sendMsgToAll (@RequestBody SauPublishedNewMsg newMsg) {
        JsonResult<String> result = new JsonResult<>();
        SRMsgRecord sRMsgRecord = new SRMsgRecord();
        sRMsgRecord.setMessageTitle(newMsg.getMessageTitle());
        sRMsgRecord.setMessageContent(newMsg.getMessageContent());
        sRMsgRecord.setReleaseTime(new Timestamp(TimeUtil.currentTime()));
        int state = messageSendService.sendGlobalMessage(sRMsgRecord);
        if(state == Operation.SUCCESSFULLY){
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS,"发送成功");
        }else if(state == Operation.FAILED){
            result.setStateCode(ResponseCode.RESPONSE_ERROR,"发送失败");
        }
        return result;
    }

    /**
     * 发送消息给本社团内的人
     * @param newMsg 消息对象
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/sau/msg/new/group",method = RequestMethod.POST)
    public JsonResult<String> sendMsgToGroup (@RequestBody SauPublishedNewMsg newMsg) {
        JsonResult<String> result = new JsonResult<>();
        SRMsgRecord sRMsgRecord = new SRMsgRecord();
        sRMsgRecord.setMessageTitle(newMsg.getMessageTitle());
        sRMsgRecord.setMessageContent(newMsg.getMessageContent());
        sRMsgRecord.setReleaseTime(new Timestamp(TimeUtil.currentTime()));
        int state = messageSendService.sendOrgMessage(sRMsgRecord);
        if(state == Operation.SUCCESSFULLY){
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS,"发送成功");
        }else if(state == Operation.FAILED){
            result.setStateCode(ResponseCode.RESPONSE_ERROR,"发送失败");
        }
        return result;
    }

    /**
     * 发送消息给自定义对象
     * @param newMsg 消息对象
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/sau/msg/new/person",method = RequestMethod.POST)
    public JsonResult sendMsgToPerson (@RequestBody SauPublishedNewMsg newMsg) {
        JsonResult result = new JsonResult<>();
        SRMsgRecord sRMsgRecord = new SRMsgRecord();
        sRMsgRecord.setMessageTitle(newMsg.getMessageTitle());
        sRMsgRecord.setMessageContent(newMsg.getMessageContent());
        sRMsgRecord.setReleaseTime(new Timestamp(TimeUtil.currentTime()));
        List<Integer> orgIdsList = new ArrayList<>();
        String msgPublishedObject = newMsg.getPublishedObject();
        if(msgPublishedObject== null){
            result.setStateCode(ResponseCode.RESPONSE_ERROR,"要发送的对象为空");
            return result;
        }
        //获取发布对象中的社团ids（实际对应的是后台的用户id）
        String[] msgPublishObjectStrs = msgPublishedObject.split(",");
        for(int i=0;i<msgPublishObjectStrs.length;i++){
            orgIdsList.add(Integer.parseInt(msgPublishObjectStrs[i]));
        }
        sRMsgRecord.setReceives(orgIdsList);
        int state = messageSendService.sendCustomMessage(sRMsgRecord);
        if(state == Operation.SUCCESSFULLY){
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS,"发送成功");
        }else if(state == Operation.FAILED){
            result.setStateCode(ResponseCode.RESPONSE_ERROR,"发送失败");
        }
        return result;
    }

    /**
     * 根据消息id删除历史发布消息(该功能暂时不要，还不清楚逻辑)
     * @param deleteMsgIdsModel 要删除的消息的id
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/sau/msg/old",method = RequestMethod.DELETE)
    public JsonResult<String> deleteMsgs (@RequestBody DeleteMsgIdsModel deleteMsgIdsModel) {
        return null;
/*        JsonResult<String> result = new JsonResult<>();
        if(deleteMsgIdsModel.getDeleteMsgIds()==null){
            result.setStateCode(ResponseCode.REQUEST_ERROR,"要删除的消息为空，不能执行");
            return result;
        }
        String[] deleteMsgIdsStr = deleteMsgIdsModel.getDeleteMsgIds().split(",");
        List<Integer> deleteMsgIdsList = new ArrayList<>();
        for(int i=0;i<deleteMsgIdsStr.length;i++){
            deleteMsgIdsList.add(Integer.parseInt(deleteMsgIdsStr[i]));
        }
        SRMsgRecord srMsgRecord = new SRMsgRecord();
        srMsgRecord.setIds(deleteMsgIdsList);
        int state = messageSendService.deleteByMessageId(srMsgRecord);
        if(state == Operation.SUCCESSFULLY ){
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS,"删除成功");
        }else{
            result.setStateCode(ResponseCode.REQUEST_ERROR,"删除失败");
        }
        return result;*/
    }

    /**
     *根据查找内容查找消息
     * @param page 查找内容
     * @return 返回消息列表
     */
    @ResponseBody
    @RequestMapping(value = "/sau/msg/old/search",method = RequestMethod.GET)
    public JsonResult<List<OldPublishMsg>> searchMsg (SearchPage page) {
        JsonResult<List<OldPublishMsg>> result = new JsonResult<>();
        List<Message> messageList = messageSendService.queryByMessageTitle(page.getFindContent(),page.getOffset(),page.getLimit());
        Integer messageNum = messageSendService.countByMessageTitle(page.getFindContent());

        List<OldPublishMsg> oldPublishMsgList = new ArrayList<>();
        for(Message message:messageList){
            OldPublishMsg oldPublishMsg = new OldPublishMsg();
            oldPublishMsg.setMessageId(message.getMessageId());
            oldPublishMsg.setMessageTitle(message.getMessageTitle());
            oldPublishMsg.setMessageType(message.getMessageType());
            oldPublishMsg.setSendTime(message.getReleaseTime());
            oldPublishMsgList.add(oldPublishMsg);
        }
        result.setStateCode(ResponseCode.RESPONSE_SUCCESS,messageNum.toString());
        result.setData(oldPublishMsgList);
        return result;
    }
}
