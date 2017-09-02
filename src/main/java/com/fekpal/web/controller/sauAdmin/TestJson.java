package com.fekpal.web.controller.sauAdmin;

import com.fekpal.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static java.lang.System.out;

/**
 * Created by hasee on 2017/8/27.
 */
@Controller
public class TestJson {
    /**
     * 测试json数据
     * 前端直接用put或者post请求都可以
     * 后端可以用list<User>或者User[]接受
     *
     */
    @RequestMapping(value = "/sau/testJson", method = RequestMethod.PUT )
    @ResponseBody
    public void saveUser(@RequestBody User[] users) {
        for(User user:users){
            out.println(user);
        }
    }
}
