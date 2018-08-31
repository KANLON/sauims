package test.dao;

import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.AvailableState;
import com.fekpal.common.constant.MessageType;
import com.fekpal.dao.mapper.MessageReceiveMapper;
import com.fekpal.dao.model.MessageReceive;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by APone on 2018/2/17 1:14.
 */
public class TestRec extends TestDao {

    @Autowired
    private MessageReceiveMapper messageReceiveMapper;

    @Before
    public void init() {
        messageReceiveMapper.insert(Model.messageReceive);
        List<MessageReceive> list = new ArrayList<>();
        list.add(Model.messageReceive);
        list.add(Model.messageReceive);
        messageReceiveMapper.insertLoop(list);
    }

    @Test
    public void test() {
        messageReceiveMapper.selectByPrimaryKey(Model.messageReceive.getId());
        messageReceiveMapper.selectByExample(new ExampleWrapper<>(), 0, 10);

        Model.messageReceive.setReadFlag(MessageType.HAVE_READ);
        messageReceiveMapper.updateByPrimaryKey(Model.messageReceive);
        messageReceiveMapper.selectByExample(new ExampleWrapper<>(), 0, 10);

        Model.messageReceive.setAvailable(AvailableState.AUDITING);
        messageReceiveMapper.updateByPrimaryKeySelective(Model.messageReceive);
        messageReceiveMapper.selectByExample(new ExampleWrapper<>(), 0, 10);

        messageReceiveMapper.deleteByPrimaryKey(Model.messageReceive.getId());
        messageReceiveMapper.selectByExample(new ExampleWrapper<>(),0,10);

    }
}
