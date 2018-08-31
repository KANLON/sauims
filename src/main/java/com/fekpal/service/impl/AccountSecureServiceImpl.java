package com.fekpal.service.impl;

import com.fekpal.api.*;
import com.fekpal.common.base.CRUDException;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.SystemRole;
import com.fekpal.common.utils.MD5Util;
import com.fekpal.common.utils.TimeUtil;
import com.fekpal.common.utils.captcha.Captcha;
import com.fekpal.common.utils.captcha.CaptchaUtil;
import com.fekpal.common.utils.msg.email.EmailMsg;
import com.fekpal.common.utils.msg.email.EmailSender;
import com.fekpal.dao.model.Org;
import com.fekpal.dao.model.User;
import com.fekpal.service.model.domain.SecureMsg;
import com.fekpal.common.session.SessionContent;
import com.fekpal.common.session.SessionLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * Created by APone on 2018/2/7 2:06.
 */
@Service
public class AccountSecureServiceImpl implements AccountSecureService {

    @Autowired
    private HttpSession session;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private SauService sauService;

    /**
     * 忘记密码
     */
    private final static String RESET = "reset";

    /**
     * 更新
     */
    private final static String UPDATE = "update";

    /**
     * 验证验证码
     *
     * @param captcha 验证码
     * @return 是否正确
     */
    private boolean isValidCaptcha(SessionContent.Captcha captcha, final String type) {
        SessionLocal sessionLocal = SessionLocal.local(session);
        boolean isValid = sessionLocal.isValidCaptcha(captcha, type);
        //正确则清除原有验证信息
        if (isValid) {
            sessionLocal.clear(type);
        }
        return isValid;
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int resetPwd(SecureMsg msg) {
        String email = SessionLocal.local(session).getCaptcha(RESET).getAuthorize();
        SessionContent.Captcha captcha = SessionContent.createCaptcha();
        captcha.setCode(msg.getCaptcha());
        captcha.setCurrentTime(msg.getCurrentTime());
        if (!isValidCaptcha(captcha, RESET)) {
            return Operation.CAPTCHA_INCORRECT;
        }

        ExampleWrapper<User> example = new ExampleWrapper<>();
        example.eq("email", email);
        User user = userService.selectFirstByExample(example);
        String salt = user.getUserKey();
        user.setPassword(MD5Util.encryptPwd(msg.getNewPassword(), salt));

        int row = userService.updateByPrimaryKeySelective(user);
        if (row > 1) throw new CRUDException("更新数量异常，数量" + row);
        return row == 0 ? Operation.FAILED : Operation.SUCCESSFULLY;
    }

    @Override
    public int forgetPwdByEmail(SecureMsg msg) {
        if (!userService.isExitEmail(msg.getEmail())) return Operation.FAILED;

        String code = CaptchaUtil.create().getCode();
        SessionContent.Captcha captcha = SessionContent.createCaptcha();
        captcha.setCode(code);
        captcha.setCreateTime(TimeUtil.currentTime());
        captcha.setActiveTime(10 * 60 * 1000);
        captcha.setAuthorize(msg.getEmail());
        SessionLocal.local(session).createCaptcha(captcha, RESET);

        EmailMsg emailMsg = new EmailMsg();
        emailMsg.setTo(msg.getEmail());
        emailMsg.setSubject("忘记密码邮箱验证");
        emailMsg.setText("您获取的验证码为：" + code + "\n有效期为10分钟，请勿泄露。如果此请求不是由您发出，请尽快修密码");
        emailSender.send(emailMsg);
        return Operation.SUCCESSFULLY;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int modifyPwd(SecureMsg msg) {
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        User user = userService.selectByPrimaryKey(accId);
        String salt = user.getUserKey();
        if (!user.getPassword().equals(MD5Util.encryptPwd(msg.getOldPassword(), salt))) {
            return Operation.FAILED;
        }

        user = new User();
        user.setUserId(accId);
        user.setPassword(MD5Util.encryptPwd(msg.getNewPassword(), salt));
        int row = userService.updateByPrimaryKeySelective(user);
        if (row > 1) throw new CRUDException("更新密码失败：" + row);
        return row == 0 ? Operation.FAILED : Operation.SUCCESSFULLY;
    }

    @Override
    public int sendModifyEmailCaptcha() {
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        User user = userService.selectByPrimaryKey(accId);
        if (user == null) return Operation.FAILED;

        String code = new Captcha().getCode();
        SessionContent.Captcha captcha = SessionContent.createCaptcha();
        captcha.setCode(code);
        captcha.setCreateTime(TimeUtil.currentTime());
        captcha.setActiveTime(1000 * 60 * 10);
        SessionLocal.local(session).createCaptcha(captcha, UPDATE);

        EmailMsg msg = new EmailMsg();
        msg.setTo(user.getEmail());
        msg.setSubject("修改邮箱安全验证码");
        msg.setText("您所申请修改邮箱安全验证码为：" + code + "\n有效期为10分钟，请勿泄露。如果此请求不是由您发出，请留意您的账户安全");
        emailSender.send(msg);
        return Operation.SUCCESSFULLY;
    }

    @Override
    public int sendModifyEmailCaptcha(SecureMsg record) {
        int accId = SessionLocal.local(session).getUserIdentity().getAccId();
        User user = userService.selectByPrimaryKey(accId);
        if (user == null) return Operation.FAILED;

        String code = new Captcha().getCode();
        SessionContent.Captcha captcha = SessionContent.createCaptcha();
        captcha.setCode(code);
        captcha.setCreateTime(TimeUtil.currentTime());
        captcha.setActiveTime(1000 * 60 * 10);
        SessionLocal.local(session).createCaptcha(captcha, UPDATE);

        EmailMsg msg = new EmailMsg();
        msg.setTo(record.getEmail());
        msg.setSubject("修改邮箱安全验证码");
        msg.setText("您所申请修改邮箱安全验证码为：" + code + "\n有效期为10分钟，请勿泄露。如果此请求不是由您发出，请留意您的账户安全");
        emailSender.send(msg);
        return Operation.SUCCESSFULLY;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int modifyEmail(SecureMsg msg) {
        SessionContent.Captcha captcha = SessionContent.createCaptcha();
        captcha.setCode(msg.getCaptcha());
        captcha.setCurrentTime(msg.getCurrentTime());
        if (!isValidCaptcha(captcha, UPDATE)) {
            return Operation.CAPTCHA_INCORRECT;
        }

        if (userService.isExitEmail(msg.getEmail())) return Operation.FAILED;

        SessionContent.UserIdentity userIdentity = SessionLocal.local(session).getUserIdentity();
        User user = new User();
        user.setUserId(userIdentity.getAccId());
        user.setEmail(msg.getNewEmail());
        int row = userService.updateByPrimaryKeySelective(user);

        int auth = userIdentity.getAuth();
        if (auth == SystemRole.SAU) {
            Org org = new Org();
            org.setOrgId(userIdentity.getUid());
            org.setContactEmail(msg.getNewEmail());
            row += sauService.updateByPrimaryKeySelective(org);
            if (row > 2 || row == 1) throw new CRUDException("更新社团邮箱异常，数量：" + row);

        } else if (auth == SystemRole.CLUB) {
            Org org = new Org();
            org.setOrgId(userIdentity.getUid());
            org.setContactEmail(msg.getNewEmail());
            row += clubService.updateByPrimaryKeySelective(org);
            if (row > 2 || row == 1) throw new CRUDException("更新校社联邮箱异常，数量：" + row);

        } else if (auth == SystemRole.PERSON) {
            user = new User();
            user.setUserId(userIdentity.getAccId());
            user.setUserName(msg.getNewEmail());
            row += userService.updateByPrimaryKeySelective(user);
            if (row > 2 || row == 1) throw new CRUDException("更新普通邮箱异常，数量：" + row);
        }
        return row == 0 ? Operation.FAILED : Operation.SUCCESSFULLY;
    }
}
