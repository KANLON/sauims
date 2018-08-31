package com.fekpal.service.impl;

import com.fekpal.api.*;
import com.fekpal.common.base.CRUDException;
import com.fekpal.common.constant.*;
import com.fekpal.common.utils.FileUtil;
import com.fekpal.common.utils.MD5Util;
import com.fekpal.common.utils.RandomUtil;
import com.fekpal.common.utils.TimeUtil;
import com.fekpal.common.utils.captcha.Captcha;
import com.fekpal.common.utils.msg.email.EmailMsg;
import com.fekpal.common.utils.msg.email.EmailSender;
import com.fekpal.dao.model.*;
import com.fekpal.service.model.domain.ClubReg;
import com.fekpal.service.model.domain.PersonReg;
import com.fekpal.service.model.domain.SauReg;
import com.fekpal.common.session.SessionContent;
import com.fekpal.common.session.SessionLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * Created by APone on 2018/2/9 0:42.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    HttpSession session;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private SauService sauService;

    @Autowired
    private ClubAuditService clubAuditService;

    /**
     * 社团注册
     */
    private static final String CLUB_REG = "club_reg";

    /**
     * 普通注册
     */
    private static final String PERSON_REG = "person_reg";


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public UniqueMsg checkExitInfo(UniqueMsg msg) {
        UniqueMsg result = new UniqueMsg();
        if (msg.getUserName() != null && userService.isExitAccount(msg.getUserName())) {
            result.setUserName("该用户名已被注册");
        }
        if (msg.getEmail() != null && userService.isExitEmail(msg.getEmail())) {
            result.setEmail("该邮箱已被使用");
        }
        if (msg.getClubName() != null && clubService.isExitClubName(msg.getClubName())) {
            result.setUserName("该社团名称已被注册");
        }
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int insertPersonReg(PersonReg reg) {
        SessionContent.Captcha captcha = SessionContent.createCaptcha();
        captcha.setAuthorize(reg.getUserName());
        captcha.setCode(reg.getCaptcha());
        captcha.setCurrentTime(reg.getCurrentTime());
        if (!isValidCaptcha(captcha, PERSON_REG)) {
            return Operation.CAPTCHA_INCORRECT;
        }

        //检测注册信息是否存在已被注册
        if (!userService.isUnique(reg.getUserName(), reg.getUserName())) {
            return Operation.FAILED;
        }

        //开始进行注册信息插入
        String salt = RandomUtil.createSalt();
        User user = new User();
        user.setUserName(reg.getUserName());
        user.setPassword(MD5Util.encryptPwd(reg.getPassword(), salt));
        user.setEmail(reg.getUserName());
        user.setLoginIp(reg.getLoginIp());
        user.setLoginTime(reg.getRegisterTime());
        user.setRegisterIp(reg.getRegisterIp());
        user.setRegisterTime(reg.getRegisterTime());
        user.setPhone(DefaultField.DEFAULT_PHONE);
        user.setAuthority(SystemRole.PERSON);
        user.setUserState(AvailableState.AVAILABLE);
        user.setUserKey(salt);
        int row = userService.insert(user);

        Person person = new Person();
        person.setUserId(user.getUserId());
        person.setPersonState(AvailableState.AVAILABLE);
        person.setNickname(DefaultField.DEFAULT_NICKNAME + user.getUserId());
        person.setLogo(DefaultField.DEFAULT_LOGO);
        person.setGender(DefaultField.DEFAULT_GENDER);
        person.setBirthday(DefaultField.DEFAULT_TIME);
        row += personService.insert(person);

        if (row != 2) throw new CRUDException("普通用户注册数据插入数量异常，数量：" + row);
        //注册成功后直接存储注册用户身份会话信息，以便直接自动登录
        SessionContent.UserIdentity identity = SessionContent.createUID();
        identity.setAccId(user.getUserId());
        identity.setUid(person.getPersonId());
        identity.setAuth(user.getAuthority());
        SessionLocal.local(session).createUserIdentity(identity);
        return Operation.SUCCESSFULLY;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int insertSauReg(SauReg reg) {
        //检测注册信息是否存在已被注册
        if (!userService.isUnique(reg.getUserName(), reg.getEmail()) || sauService.isExitSauName(reg.getSauName())) {
            return Operation.FAILED;
        }

        String salt = RandomUtil.createSalt();
        User user = new User();
        user.setUserName(reg.getUserName());
        user.setPassword(MD5Util.encryptPwd(reg.getPassword(), salt));
        user.setEmail(reg.getEmail());
        user.setLoginIp(reg.getLoginIp());
        user.setLoginTime(reg.getRegisterTime());
        user.setRegisterIp(reg.getRegisterIp());
        user.setRegisterTime(reg.getRegisterTime());
        user.setPhone(reg.getPhone());
        user.setAuthority(SystemRole.SAU);
        //测试使用，为直接通过注册
        user.setUserState(AvailableState.AVAILABLE);
        user.setUserKey(salt);
        int row = userService.insert(user);

        Org org = new Org();
        org.setUserId(user.getUserId());
        org.setOrgName(reg.getSauName());
        org.setAdminName(reg.getAdminName());
        org.setFoundTime(reg.getRegisterTime());
        org.setOrgType("校社联");
        org.setContactEmail(user.getEmail());
        org.setContactNumber(user.getPhone());
        org.setMembers(DefaultField.DEFAULT_MEMBERS);
        org.setLikeClick(DefaultField.DEFAULT_MEMBERS);
        org.setOrgLogo(DefaultField.DEFAULT_LOGO);
        //测试用，可直接注册有效
        org.setOrgState(AvailableState.AVAILABLE);
        org.setOrgAuth(SystemRole.SAU);
        row += sauService.insert(org);

        if (row != 2) throw new CRUDException("校社联用户注册数据插入数量异常，数量：" + row);
        return Operation.SUCCESSFULLY;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int insertClubReg(ClubReg reg) {
        SessionContent.Captcha captcha = SessionContent.createCaptcha();
        captcha.setAuthorize(reg.getEmail());
        captcha.setCode(reg.getCaptcha());
        captcha.setCurrentTime(reg.getCurrentTime());
        if (!isValidCaptcha(captcha, CLUB_REG)) {
            return Operation.CAPTCHA_INCORRECT;
        }

        //检测注册信息是否存在已被注册
        if (!userService.isUnique(reg.getUserName(), reg.getEmail()) || clubService.isExitClubName(reg.getClubName())) {
            return Operation.FAILED;
        }

        String salt = RandomUtil.createSalt();
        User user = new User();
        user.setUserName(reg.getUserName());
        user.setPassword(MD5Util.encryptPwd(reg.getPassword(), salt));
        user.setEmail(reg.getEmail());
        user.setLoginIp(reg.getLoginIp());
        user.setLoginTime(reg.getRegisterTime());
        user.setRegisterIp(reg.getRegisterIp());
        user.setRegisterTime(reg.getRegisterTime());
        user.setPhone(reg.getPhone());
        user.setAuthority(SystemRole.CLUB);
        user.setUserState(AvailableState.AUDITING);
        user.setUserKey(salt);
        int row = userService.insert(user);

        Org org = new Org();
        org.setUserId(user.getUserId());
        org.setOrgName(reg.getClubName());
        org.setAdminName(reg.getRealName());
        org.setFoundTime(reg.getRegisterTime());
        org.setDescription(reg.getDescription());
        org.setOrgType(reg.getClubType());
        org.setContactEmail(user.getEmail());
        org.setContactNumber(user.getPhone());
        org.setOrgLogo(DefaultField.DEFAULT_LOGO);
        org.setOrgState(AvailableState.AUDITING);
        org.setOrgView(DefaultField.DEFAULT_CLUB_OVERVIEW);
        org.setMembers(DefaultField.DEFAULT_MEMBERS);
        org.setLikeClick(DefaultField.DEFAULT_MEMBERS);
        org.setOrgAuth(SystemRole.CLUB);
        row += clubService.insert(org);

        String auditFileName;
        try {
            auditFileName = FileUtil.fileHandle(reg.getFile(), FIleDefaultPath.CLUB_AUDIT_FILE);
        } catch (Exception e) {
            throw new CRUDException("储存社团注册文件出错：" + e.getMessage());
        }

        ClubAudit clubAudit = new ClubAudit();
        clubAudit.setRegisterTime(reg.getRegisterTime());
        clubAudit.setApplyName(reg.getRealName());
        clubAudit.setAuditResult("");
        clubAudit.setAuditState(AuditState.AUDITING);
        clubAudit.setAuditTitle(reg.getClubName() + " 注册申请审核");
        clubAudit.setAuditTime(reg.getRegisterTime());
        clubAudit.setAuditDescription(DefaultField.EMPTY);
        clubAudit.setAuditState(AvailableState.AUDITING);
        clubAudit.setOrgId(org.getOrgId());
        clubAudit.setFile(auditFileName);
        row += clubAuditService.insert(clubAudit);

        if (row != 3) throw new CRUDException("社团用户注册数据插入数量异常，数量：" + row);
        return Operation.SUCCESSFULLY;
    }

    /**
     * 验证验证码
     *
     * @param captcha 验证码
     * @return 是否正确
     */
    private boolean isValidCaptcha(SessionContent.Captcha captcha, final String type) {
        SessionLocal sessionLocal = SessionLocal.local(session);
        boolean isValid = sessionLocal.isValidCaptchaWithAuth(captcha, type);
        if (isValid) sessionLocal.clear(type);
        return isValid;
    }

    /**
     * 发送验证码邮件通用操作
     *
     * @param email  邮箱地址
     * @param type   注册种类
     * @param common 对象类型
     */
    private int sendRegCaptchaByEmail(String email, final String type, String common) {
        if (userService.isExitEmail(email)) return Operation.FAILED;

        String code = new Captcha().getCode();
        SessionContent.Captcha captcha = SessionContent.createCaptcha();
        captcha.setCode(code);
        captcha.setActiveTime(1000 * 60 * 10);
        captcha.setCreateTime(TimeUtil.currentTime());
        captcha.setAuthorize(email);
        SessionLocal.local(session).createCaptcha(captcha, type);

        EmailMsg msg = new EmailMsg();
        msg.setSubject("校社联管理系统" + common + "用户注册验证码");
        msg.setText("您获取的验证码为：" + code + "\n10分钟内有效，如果不是您提出的注册申请，请留意");
        msg.setTo(email);
        emailSender.send(msg);
        return Operation.SUCCESSFULLY;
    }

    @Override
    public int sendClubEmailCaptcha(String email) {
        return sendRegCaptchaByEmail(email, CLUB_REG, "社团");
    }

    @Override
    public int sendPersonEmailCaptcha(String email) {
        return sendRegCaptchaByEmail(email, PERSON_REG, "普通");
    }
}
