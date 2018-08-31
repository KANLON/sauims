package test.dao;

import com.fekpal.common.constant.AvailableState;
import com.fekpal.common.constant.DefaultField;
import com.fekpal.common.constant.MessageType;
import com.fekpal.common.constant.SystemRole;
import com.fekpal.dao.model.*;

import java.sql.Timestamp;

/**
 * Created by APone on 2018/2/5 1:43.
 */
class Model {

    protected static User user = new User();

    protected static User anUser = new User();

    protected static Org club = new Org();

    protected static Org sau = new Org();

    protected static Person person = new Person();

    protected static ClubAudit clubAudit = new ClubAudit();

    protected static AnniversaryAudit anniversaryAudit = new AnniversaryAudit();

    protected static Message message = new Message();

    protected static Message message1 = new Message();

    protected static OrgMember orgMember = new OrgMember();

    protected static MessageReceive messageReceive=new MessageReceive();

    static {
        messageReceive.setMessageId(10);
        messageReceive.setAvailable(AvailableState.AVAILABLE);
        messageReceive.setMessageTitle("测试工程");
        messageReceive.setReadFlag(MessageType.UN_READ);
        messageReceive.setReceiveId(1);
        messageReceive.setReleaseName("小喇叭");
        messageReceive.setReleaseTime(Timestamp.valueOf("1992-01-02 01:02:09"));

        user.setUserName("zjboy");
        user.setPassword("123456");
        user.setEmail("zjboy@163.com");
        user.setPhone("12345678901");
        user.setUserKey("123456");
        user.setLoginTime(Timestamp.valueOf("1996-02-01 01:02:01"));
        user.setLoginIp("0.0.0.0");
        user.setRegisterTime(Timestamp.valueOf("1992-01-02 01:02:09"));
        user.setRegisterIp("0.0.0.0");
        user.setAuthority(SystemRole.CLUB);
        user.setUserState(1);

        anUser.setUserName("小仙女");
        anUser.setPassword("1236");
        anUser.setEmail("zy@163.com");
        anUser.setPhone("12345678901");
        anUser.setUserKey("123456");
        anUser.setLoginTime(Timestamp.valueOf("1996-02-01 01:02:01"));
        anUser.setLoginIp("0.0.0.0");
        anUser.setRegisterTime(Timestamp.valueOf("1992-01-02 01:02:09"));
        anUser.setRegisterIp("0.0.0.0");
        anUser.setAuthority(SystemRole.SAU);
        anUser.setUserState(1);

        person.setNickname("佳佳");
        person.setUserId(user.getUserId());
        person.setPersonState(AvailableState.AUDITING);
        person.setNickname(DefaultField.DEFAULT_NICKNAME + user.getUserId());
        person.setLogo(DefaultField.DEFAULT_LOGO);
        person.setGender(DefaultField.DEFAULT_GENDER);
        person.setBirthday(DefaultField.DEFAULT_TIME);


        club.setAdminName("zj");
        club.setOrgName("IT社");
        club.setFoundTime(Timestamp.valueOf("1996-1-2 01:01:01"));

        clubAudit.setOrgId(club.getOrgId());
        clubAudit.setFile("java部落");
        clubAudit.setRegisterTime(Timestamp.valueOf("1996-06-09 00:01:02"));

        anniversaryAudit.setSubmitTime(Timestamp.valueOf("1996-06-09 01:01:01"));
        anniversaryAudit.setSubmitDescription("这是it社重改版本");
        anniversaryAudit.setFileName("434DUHWDU4234HU");
        anniversaryAudit.setOrgId(club.getOrgId());

        message.setMessageAnnex("附件");
        message.setMessageContent("百团大赛");
        message.setMessageTitle("紧急通知");
        message.setReleaseName("校社联");
        message.setMessageType(MessageType.ALL);
        message.setReleaseTime(Timestamp.valueOf("1996-06-09 00:01:02"));
        message.setMessageState(AvailableState.AVAILABLE);
        message.setOrgId(1);

        message1.setMessageContent("开学大典");
        message1.setMessageTitle("校内通知");
        message1.setReleaseName("It社");
        message1.setMessageType(MessageType.CUSTOM);
        message1.setReleaseTime(Timestamp.valueOf("1996-06-09 00:01:02"));
        message1.setMessageState(AvailableState.AVAILABLE);
        message1.setOrgId(2);

        orgMember.setJoinTime(Timestamp.valueOf("1995-01-06 12:12:12"));
    }
}

