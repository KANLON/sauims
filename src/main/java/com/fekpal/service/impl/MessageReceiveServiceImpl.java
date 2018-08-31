package com.fekpal.service.impl;

import com.fekpal.api.MessageReceiveService;
import com.fekpal.common.base.BaseServiceImpl;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.AvailableState;
import com.fekpal.common.constant.MessageType;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.session.SessionLocal;
import com.fekpal.dao.mapper.MessageReceiveMapper;
import com.fekpal.dao.model.MessageReceive;
import com.fekpal.service.model.domain.SRMsgRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by APone on 2018/2/13 19:26.
 */
@Service
public class MessageReceiveServiceImpl extends BaseServiceImpl<MessageReceiveMapper, MessageReceive> implements MessageReceiveService {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Autowired
    private HttpSession session;

    @Override
    public MessageReceive selectById(int Id) {
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        ExampleWrapper<MessageReceive> example = new ExampleWrapper<>();
        example.eq("id", Id)
                .and().eq("receive_id", accId)
                .and().eq("available", AvailableState.AVAILABLE)
                .and().eq("message_state", AvailableState.AVAILABLE);

        //将未读信息更新为已读
        MessageReceive message = mapper.selectFirstByExample(example);
        if (message != null && message.getReadFlag() == MessageType.UN_READ) {
            MessageReceive receive = new MessageReceive();
            receive.setId(Id);
            receive.setReadFlag(MessageType.HAVE_READ);
            mapper.updateByPrimaryKeySelective(receive);
        }
        return message;
    }

    @Override
    public List<MessageReceive> selectByUserId(int id, int offset, int limit) {
        ExampleWrapper<MessageReceive> example = new ExampleWrapper<>();
        example.eq("receive_id", id)
                .and().eq("available", AvailableState.AVAILABLE)
                .and().eq("message_state", AvailableState.AVAILABLE)
                .orderBy("release_time", false);
        return mapper.selectByExample(example, offset, limit);
    }

    @Override
    public int countUnReadMessage() {
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        ExampleWrapper<MessageReceive> example = new ExampleWrapper<>();
        example.eq("receive_id", accId)
                .and().eq("available", AvailableState.AVAILABLE)
                .and().eq("message_state", AvailableState.AVAILABLE)
                .and().eq("read_flag", MessageType.UN_READ);
        return mapper.countByExample(example);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public void updateReceiveNote() {
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        MessageReceive receive = new MessageReceive();
        receive.setReceiveId(accId);
        receive.setReadFlag(MessageType.UN_READ);
        receive.setAvailable(AvailableState.AVAILABLE);
        executorService.submit(() -> mapper.insertCommittedNote(receive));
    }

    @Override
    public int deleteById(SRMsgRecord record) {
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        ExampleWrapper<MessageReceive> receiveExample = new ExampleWrapper<>();
        receiveExample.in("id", record.getIds())
                .and().eq("receive_id", accId)
                .and().eq("available", AvailableState.AVAILABLE);
        int row = mapper.deleteByExample(receiveExample);
        return row >= 1 ? Operation.SUCCESSFULLY : Operation.FAILED;
    }

    @Override
    public List<MessageReceive> queryByMessageTitle(String title, int offset, int limit) {
        if(StringUtils.isEmpty(title)){
            return loadAllReceiveMessage(offset,limit);
        }
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        ExampleWrapper<MessageReceive> example = new ExampleWrapper<>();
        example.eq("receive_id", accId)
                .and().like("message_title", title)
                .and().eq("message_state", AvailableState.AVAILABLE)
                .and().eq("available", AvailableState.AVAILABLE)
                .orderBy("release_time", false);
        return mapper.selectByExample(example, offset, limit);
    }

    @Override
    public Integer countByMessageTitle(String title) {
        if(StringUtils.isEmpty(title)){
            return countAllReceiveMessage();
        }
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        ExampleWrapper<MessageReceive> example = new ExampleWrapper<>();
        example.eq("receive_id", accId)
                .and().like("message_title", title)
                .and().eq("message_state", AvailableState.AVAILABLE)
                .and().eq("available", AvailableState.AVAILABLE);
        return mapper.countByExample(example);
    }

    @Override
    public List<MessageReceive> queryByReleaseName(String name, int offset, int limit) {
        if(StringUtils.isEmpty(name)){
            return loadAllReceiveMessage(offset,limit);
        }
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        ExampleWrapper<MessageReceive> example = new ExampleWrapper<>();
        example.eq("receive_id", accId)
                .and().like("release_name", name)
                .and().eq("message_state", AvailableState.AVAILABLE)
                .and().eq("available", AvailableState.AVAILABLE)
                .orderBy("release_time", false);
        return mapper.selectByExample(example, offset, limit);
    }

    @Override
    public Integer countByReleaseName(String name) {
        if(StringUtils.isEmpty(name)){
            return countAllReceiveMessage();
        }
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        ExampleWrapper<MessageReceive> example = new ExampleWrapper<>();
        example.eq("receive_id", accId)
                .and().like("release_name", name)
                .and().eq("message_state", AvailableState.AVAILABLE)
                .and().eq("available", AvailableState.AVAILABLE);
        return mapper.countByExample(example);
    }

    @Override
    public void getAnnexById(int id, OutputStream outputStream) {
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        ExampleWrapper<MessageReceive> example = new ExampleWrapper<>();
        example.eq("id", id)
                .and().eq("receive_id", accId)
                .and().eq("message_state", AvailableState.AVAILABLE)
                .and().eq("available", AvailableState.AVAILABLE);
        MessageReceive receive = mapper.selectFirstByExample(example);
    }

    @Override
    public List<MessageReceive> loadAllReceiveMessage(int offset, int limit) {
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        ExampleWrapper<MessageReceive> example = new ExampleWrapper<>();
        example.eq("receive_id", accId)
                .and().eq("available", AvailableState.AVAILABLE)
                .and().eq("message_state", AvailableState.AVAILABLE)
                .orderBy("release_time", false);
        return mapper.selectByExample(example, offset, limit);
    }

    @Override
    public Integer countAllReceiveMessage() {
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        ExampleWrapper<MessageReceive> example = new ExampleWrapper<>();
        example.eq("receive_id", accId)
                .and().eq("available", AvailableState.AVAILABLE)
                .and().eq("message_state", AvailableState.AVAILABLE);
        return mapper.countByExample(example);
    }
}
