package com.fekpal.service.impl;

import com.fekpal.api.*;
import com.fekpal.common.base.CRUDException;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.AvailableState;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.SystemRole;
import com.fekpal.common.session.SessionContent;
import com.fekpal.common.session.SessionLocal;
import com.fekpal.common.utils.MD5Util;
import com.fekpal.common.utils.TimeUtil;
import com.fekpal.common.utils.captcha.Captcha;
import com.fekpal.common.utils.captcha.CaptchaUtil;
import com.fekpal.dao.model.Org;
import com.fekpal.dao.model.Person;
import com.fekpal.dao.model.User;
import com.fekpal.service.model.domain.LoginResult;
import com.fekpal.service.model.domain.SecureMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;

/**
 * Created by APone on 2018/2/19 21:15.
 */
@Service
public class AccountAccessServiceImpl implements AccountAccessService {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private SauService sauService;

    @Autowired
    MessageReceiveService messageReceiveService;

    /**
     * 登录常量
     */
    private final static String LOGIN = "login";

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public LoginResult login(SecureMsg record) {
        //验证登录验证码是否正确
        SessionContent.Captcha captcha = SessionContent.createCaptcha();
        captcha.setCode(record.getCaptcha());
        captcha.setCurrentTime(record.getCurrentTime());
        LoginResult result = new LoginResult();
        result.setResultState(Operation.FAILED);

        if (!isValidCaptcha(captcha, LOGIN)) {
            result.setResultState(Operation.CAPTCHA_INCORRECT);
            return result;
        }

        ExampleWrapper<User> example = new ExampleWrapper<>();
        example.eq("user_name", record.getUserName()).or().eq("email", record.getUserName());
        User user = userService.selectFirstByExample(example);
        //拥有此用户信息且允许用户登录状态
        if (user == null || user.getUserState() == AvailableState.UNAVAILABLE) {
            return result;
        }

        String password = MD5Util.encryptPwd(record.getPassword(), user.getUserKey());

        if (user.getPassword().equals(password)) {
            SessionContent.UserIdentity userIdentity = SessionContent.createUID();
            int authority = user.getAuthority();
            userIdentity.setAccId(user.getUserId());
            userIdentity.setAuth(authority);

            if (authority == SystemRole.PERSON) {
                ExampleWrapper<Person> personExample = new ExampleWrapper<>();
                personExample.eq("user_id", user.getUserId());
                Person person = personService.selectFirstByExample(personExample);
                userIdentity.setUid(person.getPersonId());

            } else if (authority == SystemRole.CLUB) {
                ExampleWrapper<Org> orgExample = new ExampleWrapper<>();
                orgExample.eq("user_id", user.getUserId());
                Org org = clubService.selectFirstByExample(orgExample);
                userIdentity.setUid(org.getOrgId());

            } else if (authority == SystemRole.SAU) {
                ExampleWrapper<Org> orgExample = new ExampleWrapper<>();
                orgExample.eq("user_id", user.getUserId());
                Org org = sauService.selectFirstByExample(orgExample);
                userIdentity.setUid(org.getOrgId());
            }

            user = new User();
            user.setUserId(userIdentity.getAccId());
            user.setLoginIp(record.getLoginIp());
            user.setLoginTime(new Timestamp(record.getCurrentTime()));
            int row = userService.updateByPrimaryKeySelective(user);
            if (row != 1) throw new CRUDException("更新用户登录信息异常：" + row);

            SessionLocal.local(session).createUserIdentity(userIdentity);
            result.setResultState(Operation.SUCCESSFULLY);
            result.setAuthority(authority);
            return result;
        }
        return result;
    }

    /**
     * 验证验证码
     *
     * @param captcha 验证码
     * @return 是否正确
     */
    private boolean isValidCaptcha(SessionContent.Captcha captcha, final String type) {
        SessionLocal sessionLocal = SessionLocal.local(session);
        boolean isValid = sessionLocal.isValidCaptcha(captcha, type);
        //无论输入的验证最终结果是否正确，验证信息缓存全部清除
        sessionLocal.clear(type);
        return isValid;
    }

    @Override
    public boolean isLogin() {
        SessionLocal sessionLocal = SessionLocal.local(session);
        return sessionLocal.isExitUserIdentity();
    }

    @Override
    public boolean logout() {
        if (isLogin()) {
            session.invalidate();
            return true;
        }
        return false;
    }

    @Override
    public void sendLoginCaptchaImage(OutputStream out) {
        try {
            Captcha captchaImg = CaptchaUtil.create();

            SessionContent.Captcha captcha = SessionContent.createCaptcha();
            captcha.setCode(captchaImg.getCode());
            System.out.println("验证码为："+captchaImg.getCode());
            captcha.setCreateTime(TimeUtil.currentTime());
            captcha.setActiveTime(1000 * 60 * 2);
            //先数据存储到session，再图片流发送到客户端，否则将引起sessionID不一致
            SessionLocal.local(session).createCaptcha(captcha, LOGIN);
            captchaImg.createCaptchaImg(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
