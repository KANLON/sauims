package com.fekpal.service.impl;

import com.fekpal.api.PersonService;
import com.fekpal.common.base.BaseServiceImpl;
import com.fekpal.common.base.CRUDException;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.DefaultField;
import com.fekpal.common.constant.FIleDefaultPath;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.session.SessionLocal;
import com.fekpal.common.utils.ImageFileUtil;
import com.fekpal.dao.mapper.PersonMapper;
import com.fekpal.dao.mapper.UserMapper;
import com.fekpal.dao.model.Person;
import com.fekpal.dao.model.User;
import com.fekpal.service.model.domain.PersonMsg;
import com.fekpal.web.model.PersonDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by APone on 2017/9/5.
 * PersonService实现类
 */
@Service
public class PersonServiceImpl extends BaseServiceImpl<PersonMapper, Person> implements PersonService {

    @Autowired
    private HttpSession session;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public String updateLogo(PersonMsg msg) {
        try {
            int uid = SessionLocal.local(session).getUserIdentity().getUid();
            Person person = mapper.selectByPrimaryKey(uid);
            //存入数据库的是带后缀的，进行存储的时候是不能带后缀的，要以上传文件的后缀为后缀
            String[] orgLogos = person.getLogo().split("\\.");
            String logo = "";
            if(person.getLogo().equalsIgnoreCase(DefaultField.DEFAULT_LOGO)) {
                logo = ImageFileUtil.handle(msg.getLogo(), FIleDefaultPath.PERSON_LOGO_FILE);
            }else{
                logo = ImageFileUtil.handle(msg.getLogo(), FIleDefaultPath.PERSON_LOGO_FILE,orgLogos[0]);
            }
            person.setLogo(logo);
            mapper.updateByPrimaryKey(person);
            return logo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int updatePersonInfo(PersonMsg msg) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<Person> example = new ExampleWrapper<>();
        example.eq("nickname", msg.getNickname()).and().ne("person_id",uid);
        int row = mapper.countByExample(example);
        if  (row != 0  ){
            return Operation.INPUT_INCORRECT;
        }

        Person person = new Person();
        person.setPersonId(uid);
        person.setRealName(msg.getRealName());
        person.setStudentId(msg.getStudentId());
        person.setNickname(msg.getNickname());
        person.setGender(msg.getGender());
        person.setBirthday(msg.getBirthday());
        person.setDepartment(msg.getDepartment());
        person.setMajor(msg.getMajor());
        person.setAddress(msg.getAddress());
        person.setEnrollmentYear(msg.getEnrollmentYear());
        person.setDescription(msg.getDescription());
        row = mapper.updateByPrimaryKeySelective(person);

        if (row > 1) {throw new CRUDException("更新普通用户信息异常：" + row);}
        return row == 0 ? Operation.FAILED : Operation.SUCCESSFULLY;
    }

    @Override
    public Person selectByPrimaryId() {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        return mapper.selectByPrimaryKey(uid);
    }

    @Override
    public Person selectByUserId(int id) {
        ExampleWrapper<Person> example = new ExampleWrapper<>();
        example.eq("user_id", id);
        return mapper.selectFirstByExample(example);
    }

    @Override
    public Person selectByNickname(String name) {
        ExampleWrapper<Person> example = new ExampleWrapper<>();
        example.eq("nickname", name);
        return mapper.selectFirstByExample(example);
    }

    @Override
    public List<Person> queryByNickname(String name, int offset, int limit) {
        ExampleWrapper<Person> example = new ExampleWrapper<>();
        example.like("nickname", name);
        return mapper.selectByExample(example, offset, limit);
    }


    @Override
    public boolean isExitNickname(String name) {
        ExampleWrapper<Person> example = new ExampleWrapper<>();
        example.eq("nickname", name);
        int row = mapper.countByExample(example);
        return row >= 1;
    }

    @Override
    public List<Person> loadAllPerson(int offset, int limit) {
        ExampleWrapper<Person> example = new ExampleWrapper<>();
        return mapper.selectByExample(example, offset, limit);
    }

    /**
     * 得到个人中心的的详细信息
     * @return 个人中心的详细信息类
     */
    @Override
    public PersonDetail selectPersonDetailByPrimaryId(){
        int userId = SessionLocal.local(session).getUserIdentity().getAccId();
        int personId = SessionLocal.local(session).getUserIdentity().getUid();
        Person person = mapper.selectByPrimaryKey(personId);
        PersonDetail detail = new PersonDetail();
        User user = userMapper.selectByPrimaryKey(userId);

        detail.setAddress(person.getAddress());
        detail.setBirthday(person.getBirthday());
        detail.setDepartment(person.getDepartment());
        detail.setDescription(person.getDescription());
        detail.setEnrollmentYear(person.getEnrollmentYear());
        detail.setGender(person.getGender());
        detail.setLogo(person.getLogo());
        detail.setMajor(person.getMajor());
        detail.setNickname(person.getNickname());
        detail.setRealName(person.getRealName());
        detail.setStudentId(person.getStudentId());
        detail.setPhone(user.getPhone());

        return detail;
    }

    /**
     * 得到个人头像
     *
     * @param output 输出流
     * @return 是否成功
     */
    @Override
    public int getPersonLogo(OutputStream output) {
        int personId = SessionLocal.local(session).getUserIdentity().getUid();
        Person person = mapper.selectByPrimaryKey(personId);
        String logoName = person.getLogo();
        try {
            byte[] buffer = new byte[2048];
            // 打开图片文件
            File file = new File(FIleDefaultPath.PERSON_LOGO_FILE + logoName);
            FileInputStream fos = new FileInputStream(file.getPath());
            int count;
            while ((count = fos.read(buffer)) > 0) {
                output.write(buffer, 0, count);
            }
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
            return Operation.FAILED;
        }
        return Operation.SUCCESSFULLY;
    }


}
