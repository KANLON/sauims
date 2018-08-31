package com.fekpal.service.impl;

import com.fekpal.api.ClubService;
import com.fekpal.api.MessageSendService;
import com.fekpal.common.base.BaseServiceImpl;
import com.fekpal.common.base.CRUDException;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.*;
import com.fekpal.common.session.SessionContent;
import com.fekpal.common.session.SessionLocal;
import com.fekpal.common.utils.FileUtil;
import com.fekpal.dao.mapper.*;
import com.fekpal.dao.model.*;
import com.fekpal.service.model.domain.SRMsgRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by APone on 2017/9/17.
 *
 * @author APone
 * @date 2017/9/17
 */
@Service
public class MessageSendServiceImpl extends BaseServiceImpl<MessageMapper, Message> implements MessageSendService {

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private MessageReceiveMapper receiveMapper;

    @Autowired
    private OrgMemberMapper orgMemberMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpSession session;

    @Autowired
    private ClubService clubService;

    /**
     * 一次循环最大数目
     */
    private int maxItem = 100000;

    @Override
    public Message selectByMessageId(int id) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<Message> example = new ExampleWrapper<>();
        example.eq("message_id", id)
                .and().eq("org_id", uid)
                .and().eq("message_state", AvailableState.AVAILABLE);
        return mapper.selectFirstByExample(example);
    }

    @Override
    public List<Message> queryByMessageTitle(String title, int offset, int limit) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<Message> example = new ExampleWrapper<>();
        example.eq("org_id", uid)
                .and().like("message_title", title)
                .and().eq("message_state", AvailableState.AVAILABLE)
                .orderBy("release_time", false);
        if (StringUtils.isEmpty(title)) {
            example = new ExampleWrapper<>();
            example.eq("org_id", uid)
                    .and().eq("message_state", AvailableState.AVAILABLE)
                    .orderBy("release_time", false);
        }
        return mapper.selectByExample(example, offset, limit);
    }

    @Override
    public Integer countByMessageTitle(String title) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<Message> example = new ExampleWrapper<>();
        example.eq("org_id", uid)
                .and().like("message_title", title)
                .and().eq("message_state", AvailableState.AVAILABLE);
        if (StringUtils.isEmpty(title)) {
            example = new ExampleWrapper<>();
            example.eq("org_id", uid)
                    .and().eq("message_state", AvailableState.AVAILABLE)
                    .orderBy("release_time", false);
        }
        return mapper.countByExample(example);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int deleteByMessageId(SRMsgRecord record) {
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        ExampleWrapper<MessageReceive> sendExample = new ExampleWrapper<>();
        sendExample.in("message_id", record.getIds())
                .and().eq("receive_id", accId)
                .and().eq("available", AvailableState.AVAILABLE);
        int row = receiveMapper.deleteByExample(sendExample);
        return row >= 1 ? Operation.SUCCESSFULLY : Operation.FAILED;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int sendGlobalMessage(SRMsgRecord record) {
        Message message = new Message();
        try {
            String fileName = addAnnex(record);
            message.setMessageAnnex(fileName);
        } catch (Exception e) {
            return Operation.FAILED;
        }
        SessionContent.UserIdentity userIdentity = SessionLocal.local(session).getUserIdentity();
        String releaseName = getReleaseNameByUserId(userIdentity);
        if (releaseName == null) {
            return Operation.FAILED;
        }

        int uid = userIdentity.getUid();
        message.setOrgId(uid);
        message.setMessageTitle(record.getMessageTitle());
        message.setMessageContent(record.getMessageContent());
        message.setReleaseName(releaseName);
        message.setReleaseTime(record.getReleaseTime());
        message.setMessageType(MessageType.ALL);
        message.setMessageState(AvailableState.AVAILABLE);
        int row = mapper.insert(message);
        if (row != 1) {
            throw new CRUDException("全体信息插入异常：" + row);
        }

        MessageReceive receive = new MessageReceive();
        receive.setMessageId(message.getMessageId());
        receive.setReadFlag(MessageType.UN_READ);
        receive.setAvailable(AvailableState.AVAILABLE);

        //获取所有人的userId
        List<Integer> receives = new ArrayList<>();
        ExampleWrapper<User> userExample = new ExampleWrapper<>();
        List<User> userList = userMapper.selectByExample(userExample, 0, 100000);
        if (userList == null) {
            return Operation.FAILED;
        }
        for (User user : userList) {
            receives.add(user.getUserId());
        }
        //批量一定长度插入
        int size = receives.size(), loopCount = (size % maxItem == 0) ? (size / maxItem) : (size / maxItem + 1);
        for (int i = 0; i < loopCount; i++) {
            int from = i * maxItem, to = (size > from + maxItem) ? from + maxItem : size;
            receive.setReceives(receives.subList(from, to));
            row += receiveMapper.insertLoopOnlyWithReceiveId(receive);
        }
        if (row != size + 1) {
            throw new CRUDException("插入接收人信息出错，发送人和接收人数总数：" + size + " 实际插入：" + row);
        }

        return Operation.SUCCESSFULLY;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int sendCustomMessage(SRMsgRecord record) {
        Message message = new Message();
        try {
            String fileName = addAnnex(record);
            message.setMessageAnnex(fileName);
        } catch (Exception e) {
            throw new CRUDException("添加附件失败");
        }

        SessionContent.UserIdentity userIdentity = SessionLocal.local(session).getUserIdentity();
        String releaseName = getReleaseNameByUserId(userIdentity);
        int uid = userIdentity.getUid();

        message.setOrgId(uid);
        message.setMessageTitle(record.getMessageTitle());
        message.setMessageContent(record.getMessageContent());
        message.setReleaseName(releaseName);
        message.setReleaseTime(record.getReleaseTime());
        message.setMessageType(MessageType.CUSTOM);
        message.setMessageState(AvailableState.AVAILABLE);
        int row = mapper.insert(message);

        MessageReceive receive = new MessageReceive();
        receive.setMessageId(message.getMessageId());
        receive.setReadFlag(MessageType.UN_READ);
        receive.setAvailable(AvailableState.AVAILABLE);
        List<Integer> receives = record.getReceives();


        //批量一定长度插入
        int size = receives.size(), loopCount = (size % maxItem == 0) ? (size / maxItem) : (size / maxItem + 1);
        for (int i = 0; i < loopCount; i++) {
            int from = i * maxItem, to = (size > from + maxItem) ? from + maxItem : size;
            receive.setReceives(receives.subList(from, to));
            row += receiveMapper.insertLoopOnlyWithReceiveId(receive);
        }
        if (row != size + 1) throw new CRUDException("插入接收人信息出错，发送人和接收人数总数：" + size + " 实际插入：" + row);
        return Operation.SUCCESSFULLY;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int sendOrgMessage(SRMsgRecord record) {
        Message message = new Message();
        try {
            String fileName = addAnnex(record);
            message.setMessageAnnex(fileName);
        } catch (Exception e) {
            return Operation.FAILED;
        }

        SessionContent.UserIdentity userIdentity = SessionLocal.local(session).getUserIdentity();
        String releaseName = getReleaseNameByUserId(userIdentity);
        int uid = userIdentity.getUid();

        message.setOrgId(uid);
        message.setMessageTitle(record.getMessageTitle());
        message.setMessageContent(record.getMessageContent());
        message.setReleaseName(releaseName);
        message.setReleaseTime(record.getReleaseTime());
        message.setMessageType(MessageType.Org);
        message.setMessageState(AvailableState.AVAILABLE);
        int row = mapper.insert(message);

        MessageReceive receive = new MessageReceive();
        receive.setMessageId(message.getMessageId());
        receive.setReadFlag(MessageType.UN_READ);
        receive.setAvailable(AvailableState.AVAILABLE);

        List<Integer> receives = new ArrayList<>();
        //获取该用户所在的组织所有仍未离开组织的社员的userId
        List<Person> personList = clubService.getClubAllMemberPersonMsg(0, 100000);
        if (personList != null) {
            for (Person person : personList) {
                receives.add(person.getUserId());
            }
        }
        //批量一定长度插入
        int size = receives.size(), loopCount = (size % maxItem == 0) ? (size / maxItem) : (size / maxItem + 1);
        for (int i = 0; i < loopCount; i++) {
            int from = i * maxItem, to = (size > from + maxItem) ? from + maxItem : size;
            receive.setReceives(receives.subList(from, to));
            row += receiveMapper.insertLoopOnlyWithReceiveId(receive);
        }

        return Operation.SUCCESSFULLY;
    }

    /**
     * 添加附件文件
     *
     * @param record 发送信息封装
     * @return 添加状态
     */
    private String addAnnex(SRMsgRecord record) throws IOException {
        MultipartFile annex = record.getMessageAnnex();
        if (annex != null) {
            return FileUtil.fileHandle(annex, FIleDefaultPath.MESSAGE_ANNEX_FILE);
        }
        return null;
    }

    /**
     * 获取发送人名称
     *
     * @param identity 用户身份
     * @return 发送人名称
     */
    private String getReleaseNameByUserId(SessionContent.UserIdentity identity) {
        Org org = orgMapper.selectByPrimaryKey(identity.getUid());
        return org != null ? org.getOrgName() : null;
    }

    @Override
    public void getAnnexByMessageId(int id, OutputStream outputStream) {
        ExampleWrapper<Message> example = new ExampleWrapper<>();
        example.eq("message_id", id).and().eq("message_state", AvailableState.AVAILABLE);
        Message message = mapper.selectFirstByExample(example);
        message.getMessageAnnex();
    }

    @Override
    public List<Message> loadAllMessage(int offset, int limit) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<Message> example = new ExampleWrapper<>();
        example.eq("org_id", uid)
                .and().eq("message_state", AvailableState.AVAILABLE)
                .orderBy("release_time", false);
        return mapper.selectByExample(example, offset, limit);
    }

    @Override
    public Integer countAllMessage(int offset, int limit) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<Message> example = new ExampleWrapper<>();
        example.eq("org_id", uid)
                .and().eq("message_state", AvailableState.AVAILABLE);
        return mapper.countByExample(example);
    }
}
