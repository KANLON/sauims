package com.fekpal.service;

import com.fekpal.domain.Message;
import com.fekpal.domain.MessageRelease;

import java.util.List;

/**
 * Created by APone on 2017/8/25.
 * MessageService接口
 */
public interface MessageService {

    /**
     * 根据消息id获得消息
     *
     * @param id int
     * @return Message
     */
    Message getSendMessageByMessageId(int id);

    /**
     * 根据用户id获得消息列表,可代替loadAllMessage并指定特定的用户id的Message
     *
     * @param id    int
     * @param start int
     * @param count int
     * @return List
     */
    List<Message> getSendMessageByUserId(int id, int start, int count);

    /**
     * 根据消息标题查找
     *
     * @param message Message
     * @param start   int
     * @param count   int
     * @return List
     */
    List<Message> findSendMessageByTitle(Message message, int start, int count);

    /**
     * 添加消息,以及添加接收人列表
     *
     * @param message   Message
     * @param rcvIdList List
     */
    void addNewSendMessage(Message message, List<Integer> rcvIdList);

    /**
     * 更新消息
     *
     * @param message Message
     */
    void updateSendMessage(Message message);

    /**
     * 根据消息id获得专属消息
     *
     * @param id int
     * @return MessageRelease
     */
    MessageRelease getRcvMessageByReleaseId(int id);

    /**
     * 根据用户id获得
     *
     * @param id    int
     * @param start int
     * @param count int
     * @return List
     */
    List<MessageRelease> getRvcMessagesByRcvId(int id, int start, int count);


    /**
     * 根据标题查找专属消息
     *
     * @param release MessageRelease
     * @param start   int
     * @param count   int
     * @return List
     */
    List<MessageRelease> findRcvMessageByTitle(MessageRelease release, int start, int count);

    /**
     * 根据接收人id更新接收消息
     *
     * @param release   MessageRelease
     * @param rcvIdList List
     */
    void updateRcvMessage(MessageRelease release, List<Integer> rcvIdList);
}
