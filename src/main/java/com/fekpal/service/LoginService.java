package com.fekpal.service;

import com.fekpal.domain.User;
import org.springframework.stereotype.Service;

/**
 * 用来登陆用户功能的服务层
 * Created by hasee on 2017/8/16.
 */
@Service
public class LoginService {
    /**
     * 根据用户名返回用户类
     * @param userName
     * @return
     */
    public User getUserByName(String userName){
        //模拟user的数据
        User user = new User();
        user.setUserId(1);
        user.setuserName(userName);
        user.setKey("123");
        user.setPassword("123456");
        user.setPhone("1816821383");
        return user;
    }
}
