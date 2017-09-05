package com.fekpal.dao;

import com.fekpal.domain.Message;
import com.fekpal.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by APone on 2017/8/23.
 * messageDao接口
 */
@Repository
public interface MessageDao {

    /**
     * 根据消息id获得消息
     *
     * @param messageId int
     * @return Message
     */
    Message getMessageByMessageId(int messageId);

    /**
     * 根据用户id获得消息列表,可代替loadAllMessage并指定特定的用户id的Message
     *
     * @param userId int
     * @param start  int
     * @param count  int
     * @return List
     */
    List<Message> getMessagesByUserId(int userId, int start, int count);

    /**
     * 根据消息标题查找
     *
     * @param messageTitle String
     * @param userId       int
     * @param start        int
     * @param count        int
     * @return List
     */
    List<Message> findMessageByMessageTitle(String messageTitle, int userId, int start, int count);

    /**
     * 添加消息
     *
     * @param message Message
     */
    void addMessage(Message message);

    /**
     * 更新消息
     *
     * @param message Message
     */
    void updateMessage(Message message);
}
