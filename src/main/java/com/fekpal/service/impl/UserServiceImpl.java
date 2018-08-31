package com.fekpal.service.impl;

import com.fekpal.api.UserService;
import com.fekpal.common.base.BaseServiceImpl;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.dao.mapper.UserMapper;
import com.fekpal.dao.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by APone on 2017/9/5.
 * UserService用户服务实现类
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User selectByUserName(String userName) {
        ExampleWrapper<User> example = new ExampleWrapper<>();
        example.eq("user_name", userName);
        return mapper.selectFirstByExample(example);
    }

    @Override
    public User selectByUserNameAndPwd(User user) {
        ExampleWrapper<User> example = new ExampleWrapper<>();
        example.eq("user_name", user.getUserName()).and().eq("password", user.getPassword());
        return mapper.selectFirstByExample(example);
    }

    @Override
    public User selectByEmail(String email) {
        ExampleWrapper<User> example = new ExampleWrapper<>();
        example.eq("email", email);
        return mapper.selectFirstByExample(example);
    }

    @Override
    public boolean isExitPhone(String phone) {
        ExampleWrapper<User> example = new ExampleWrapper<>();
        example.eq("phone", phone);
        int row = mapper.countByExample(example);
        return row >= 1;
    }

    @Override
    public boolean isExitAccount(String userName) {
        ExampleWrapper<User> example = new ExampleWrapper<>();
        example.eq("user_name", userName);
        int row = mapper.countByExample(example);
        return row >= 1;
    }

    @Override
    public boolean isExitEmail(String email) {
        ExampleWrapper<User> example = new ExampleWrapper<>();
        example.eq("email", email);
        int row = mapper.countByExample(example);
        return row >= 1;
    }

    @Override
    public boolean isUnique(String userName, String email) {
        ExampleWrapper<User> example = new ExampleWrapper<>();
        example.eq("user_name", userName).or().eq("email", email);
        int row = mapper.countByExample(example);
        return row == 0;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int updateLastLoginById(User user) {
        if (user.getUserId() != null && user.getLoginIp() != null && user.getLoginTime() != null) {
            return mapper.updateByPrimaryKeySelective(user);
        }
        return 0;
    }

    @Override
    public List<User> loadAllUser(Integer offset, Integer limit) {
        ExampleWrapper<User> example = new ExampleWrapper<>();
        return mapper.selectByExample(example, offset, limit);
    }
}
