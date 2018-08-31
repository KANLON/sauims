package com.fekpal.dao.mapper;

import com.fekpal.common.base.BaseMapper;
import com.fekpal.dao.model.MessageReceive;
import org.springframework.stereotype.Repository;

/**
 * Created by APone on 2017/8/24.
 */
@Repository
public interface MessageReceiveMapper extends BaseMapper<MessageReceive> {

    /**
     * 插入尚未在接收公告记录表的信息本体记录
     *
     * @param receive 接收信息记录
     * @return 插入数量
     */
    int insertCommittedNote(MessageReceive receive);

    /**
     * 批量插入相同接收信息但接收人标识集不一样的记录
     *
     * @param receive 接收信息记录
     * @return 插入数量
     */
    int insertLoopOnlyWithReceiveId(MessageReceive receive);
}
