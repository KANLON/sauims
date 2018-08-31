package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.MessageReceive;
import com.fekpal.service.model.domain.SRMsgRecord;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by APone on 2018/2/13 19:26.
 * 信息接收服务接口
 * 该接口提供信息接收者用于接收，删除，查看信息的操作
 */
public interface MessageReceiveService extends BaseService<MessageReceive> {

    /**
     * 根据接收信息标识获取该用户接收的原始有效信息记录
     *
     * @param id 信息标识
     * @return 原始信息记录
     */
    MessageReceive selectById(int id);

    /**
     * 根据用户身份标识获取该用户接收的有效信息记录集
     *
     * @param id     用户身份标识
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 接收信息记录集
     */
    List<MessageReceive> selectByUserId(int id, int offset, int limit);

    /**
     * 统计该用户所有未读的有效信息数量
     *
     * @return 统计数量
     */
    int countUnReadMessage();

    /**
     * 根据信息标识逻辑删除该用户所接收的有效信息记录集
     *
     * @param record 信息封装
     *               传入参数：删除集合ids
     * @return 删除状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int deleteById(SRMsgRecord record);

    /**
     * 异步将用户尚未拥有的全体信息的记录更新到接收记录里
     */
    void updateReceiveNote();

    /**
     * 根据信息标题模糊搜索该用户的接收的按时间降序的有效信息记录集，按分页获取
     *
     * @param title  信息标题
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 接收信息记录集
     */
    List<MessageReceive> queryByMessageTitle(String title, int offset, int limit);

    /**
     * 根据信息标题模糊搜索该用户的接收的有效信息记录数
     *
     * @param title  信息标题
     * @return 接收信息记录数
     */
    Integer countByMessageTitle(String title);

    /**
     * 根据信息发送人名称模糊搜索该用户接收的按时间降序的有效信息记录集，按分页获取
     *
     * @param name   发送人名称
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 接收信息记录集
     */
    List<MessageReceive> queryByReleaseName(String name, int offset, int limit);

    /**
     * 根据信息发送人名称模糊搜索该用户接收的有效信息记录数
     *
     * @param name   发送人名称
     * @return 接收信息记录数
     */
    Integer countByReleaseName(String name);

    /**
     * 获取该用户接收的有效信息的附件，前提该信息具有附件
     *
     * @param id           发送信息标识
     * @param outputStream 输出流
     */
    void getAnnexById(int id, OutputStream outputStream);

    /**
     * 获取所有该用户接收的按时间降序的有效信息记录，按分页获取
     *
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 接收信息记录集
     */
    List<MessageReceive> loadAllReceiveMessage(int offset, int limit);

    /**
     * 获取所有该用户接收的按时间降序的有效信息记录数
     *
     * @return 接收信息记录数
     */
    Integer countAllReceiveMessage();
}
