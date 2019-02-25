package com.fekpal.web.controller;

import com.fekpal.api.MessageReceiveService;
import com.fekpal.api.MessageSendService;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import com.fekpal.dao.model.MessageReceive;
import com.fekpal.service.model.domain.SRMsgRecord;
import com.fekpal.web.model.DeleteMsgIdsModel;
import com.fekpal.web.model.NewMsgDetail;
import com.fekpal.web.model.NewMsgListDomain;
import com.fekpal.web.model.SearchPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 新消息的控制类
 *
 * @author zhangcanlong
 * @date 2017/8/22
 */
@Controller
public class ReceiveMsgController {

    @Autowired
    private MessageSendService messageSendService;

    @Autowired
    private MessageReceiveService messageReceiveService;

    static Logger logger =   LogManager.getLogger(ReceiveMsgController.class);

    /**
     * 根据用户id返回全部有效消息
     *
     * @return json
     */
    @ResponseBody
    @RequestMapping(value = "/msg", method = RequestMethod.GET)
    public JsonResult<List<NewMsgListDomain>> getAllMsg(SearchPage page) {
        return searchMsg(page);
    }

    /**
     * 根据用户ID和消息id返回某条消息的详细内容信息
     * 这里的消息id，指的是消息接收表对应的主键id
     *
     * @return json
     */
    @ResponseBody
    @RequestMapping(value = "/msg/{messageId}", method = RequestMethod.GET)
    public JsonResult<NewMsgDetail> getMsgById(@PathVariable Integer messageId) {
        JsonResult<NewMsgDetail> jsonResult = new JsonResult<>();
        MessageReceive message = messageReceiveService.selectById(messageId);

        if (message == null) {
            jsonResult.setStateCode(ResponseCode.REQUEST_ERROR, "所要查询的消息不存在");
        } else {
            NewMsgDetail msgDetail = new NewMsgDetail();
            msgDetail.setMessageId(message.getId());
            msgDetail.setMessageAnnex(message.getMessageAnnex());
            msgDetail.setMessageContent(message.getMessageContent());
            msgDetail.setMessageTitle(message.getMessageTitle());
            msgDetail.setMessageType(message.getMessageType());
            msgDetail.setOrgId(message.getOrgId());
            msgDetail.setReadFlag(message.getReadFlag());
            msgDetail.setReceives(message.getReceives());
            msgDetail.setReleaseName(message.getReleaseName());
            msgDetail.setReleaseTime(message.getReleaseTime());

            jsonResult.setData(msgDetail);
            jsonResult.setStateCode(ResponseCode.RESPONSE_SUCCESS, "获取成功");
        }
        logger.info("执行了某条消息的详细内容信息");
        return jsonResult;
    }

    /**
     * 删除该用户的所发的信息
     *
     * @return json
     */
    @ResponseBody
    @RequestMapping(value = "/msg", method = RequestMethod.DELETE)
    public JsonResult<String> deleteMsg(@RequestBody DeleteMsgIdsModel deleteMsgIdsModel) {
        JsonResult<String> jsonResult = new JsonResult<>();
        //将从前端获取到要删除的消息id转化为List集合
        String deleteMsgIds = deleteMsgIdsModel.getDeleteMsgIds();
        logger.info("删除的消息集合" + deleteMsgIds);
        List<String> deleteMsgIdsListTemp = Arrays.asList(deleteMsgIds.split(","));
        List<Integer> deleteMsgIdsList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(deleteMsgIdsListTemp)) {
            for (String ids : deleteMsgIdsListTemp) {
                deleteMsgIdsList.add(Integer.parseInt(ids));
            }
        } else {
            jsonResult.setStateCode(ResponseCode.REQUEST_ERROR, "要删除的消息为空，非法操作");
            return jsonResult;
        }
        //获取要删除的消息id的list集合，放入服务层接受消息的记录值
        SRMsgRecord sRMsgRecord = new SRMsgRecord();
        sRMsgRecord.setIds(deleteMsgIdsList);
        int state = messageReceiveService.deleteById(sRMsgRecord);
        if (state == Operation.FAILED) {
            jsonResult.setStateCode(ResponseCode.REQUEST_ERROR, "删除失败，消息不存在");
        } else {
            jsonResult.setStateCode(ResponseCode.RESPONSE_SUCCESS, "删除成功");
        }
        logger.info("执行了删除某些信息");
        return jsonResult;
    }

    /**
     * 根据查找内容从查找标题相关消息
     *
     * @return json
     */
    @ResponseBody
    @RequestMapping(value = "/msg/search", method = RequestMethod.GET)
    public JsonResult<List<NewMsgListDomain>> searchMsg(SearchPage page) {
        JsonResult<List<NewMsgListDomain>> jsonResult = new JsonResult<>();
        List<MessageReceive> messageReceiveList = messageReceiveService.queryByMessageTitle(page.getFindContent(), page.getOffset(), page.getLimit());
        Integer messageNum = messageReceiveService.countByMessageTitle(page.getFindContent());
        List<NewMsgListDomain> msgList = new ArrayList<>();
        if (messageReceiveList != null) {
            for (MessageReceive messageReceive : messageReceiveList) {
                NewMsgListDomain msgListDomain = new NewMsgListDomain();
                msgListDomain.setMessageId(messageReceive.getId());
                msgListDomain.setMessageTitle(messageReceive.getMessageTitle());
                msgListDomain.setReadFlag(messageReceive.getReadFlag());
                msgListDomain.setReleaseName(messageReceive.getReleaseName());
                msgListDomain.setReleaseTime(messageReceive.getReleaseTime());
                msgList.add(msgListDomain);
            }
            jsonResult.setStateCode(ResponseCode.RESPONSE_SUCCESS, messageNum.toString());
            jsonResult.setData(msgList);
        }
        logger.info("执行了查找信息");
        return jsonResult;
    }
}
