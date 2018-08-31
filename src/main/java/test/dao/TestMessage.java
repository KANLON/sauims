package test.dao;

import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.AvailableState;
import com.fekpal.common.utils.MD5Util;
import com.fekpal.dao.mapper.AuditsViewMapper;
import com.fekpal.dao.mapper.MessageMapper;
import com.fekpal.dao.mapper.MessageReceiveMapper;
import com.fekpal.dao.model.AuditsView;
import com.fekpal.dao.model.Message;
import com.fekpal.dao.model.MessageReceive;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by APone on 2018/2/17 0:19.
 */
public class TestMessage extends TestDao {

    @Autowired
    public MessageMapper messageMapper;

    @Autowired
    private MessageReceiveMapper receiveMapper;

    @Autowired
    private AuditsViewMapper auditsViewMapper;

    @Before
    public void init() {
        ExampleWrapper<AuditsView> exampleWrapper = new ExampleWrapper<>();
        System.out.println(auditsViewMapper.selectByExample(exampleWrapper, 0, 40));
        /*
        messageMapper.insert(Model.message);
        List<Message> list = new ArrayList<>();
        list.add(Model.message);
        list.add(Model.message1);
        messageMapper.insertLoop(list);
        */
/*
        List<Integer> receives = new ArrayList<>();
        for (int i = 0; i < 1365; i++) {
            receives.add(i);
        }

        MessageReceive receive = new MessageReceive();
        receive.setMessageId(1);
        receive.setReadFlag(1);
        receive.setReadFlag(9);

        //批量一定长度插入
        int maxItem = 90;
        int size = receives.size(), loopCount = (size % maxItem == 0) ? (size / maxItem) : (size / maxItem + 1);
        int row = 0;
        for (int i = 0; i < loopCount; i++) {
            int from = i * maxItem, to = (size > from + maxItem) ? from + maxItem : size;
            receive.setReceives(receives.subList(from, to));
            row += receiveMapper.insertLoopOnlyWithReceiveId(receive);
        }
        System.out.println(row);
*/
        //receiveMapper.insertLoopOnlyWithReceiveId(receive);
    }

    @Test
    public void test1() {
        //System.out.println(MD5Util.md5("12345" + "B7NIO6OGawvB1YhZI1xT7g=="));
        /*
        messageMapper.selectByPrimaryKey(Model.message.getMessageId());
        messageMapper.selectByExample(new ExampleWrapper<>(), 0, 10);
        Model.message.setMessageState(AvailableState.UNAVAIABLE);
        messageMapper.updateByPrimaryKey(Model.message);
        messageMapper.selectByExample(new ExampleWrapper<>(), 0, 10);
        Model.message.setMessageState(AvailableState.AUDITING);
        messageMapper.updateByPrimaryKeySelective(Model.message);
        messageMapper.selectByExample(new ExampleWrapper<>(), 0, 10);
        messageMapper.deleteByPrimaryKey(Model.message.getMessageId());
        messageMapper.selectByExample(new ExampleWrapper<>(), 0, 10);
        */

    }
}

