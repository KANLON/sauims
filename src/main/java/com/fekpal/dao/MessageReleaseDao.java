package com.fekpal.dao;

import com.fekpal.domain.MessageRelease;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by APone on 2017/8/24.
 * MessageReleaseDao接口
 */
@Repository
public interface MessageReleaseDao {

    /**
     * 根据消息id获得专属消息
     *
     * @param id int
     * @return MessageRelease
     */
    MessageRelease getMessageByMessageReleaseId(int id);

    /**
     * 根据用户id获得
     *
     * @param id int
     * @param start     int
     * @param count     int
     * @return List
     */
    List<MessageRelease> getMessagesByReceiveId(int id, int start, int count);


    /**
     * 根据标题查找专属消息
     *
     * @param title String
     * @param start        int
     * @param count        int
     * @return List
     */
    List<MessageRelease> findMessageByMessageTitle(String title, int receiveId, int start, int count);

    /**
     * 添加专属消息
     *
     * @param list List
     */
    void addMessageRelease(List<MessageRelease> list);

    /**
     * 更新消息
     *
     * @param messageRelease MessageRelease
     * @param list    List
     */
    void updateMessageRelease(MessageRelease messageRelease, List<Integer> list);

}
