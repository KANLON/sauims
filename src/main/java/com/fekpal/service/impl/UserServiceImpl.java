package com.fekpal.service.impl;

import com.fekpal.dao.ClubDao;
import com.fekpal.dao.PersonDao;
import com.fekpal.dao.SauDao;
import com.fekpal.dao.UserDao;
import com.fekpal.domain.Club;
import com.fekpal.domain.Person;
import com.fekpal.domain.Sau;
import com.fekpal.domain.User;
import com.fekpal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by APone on 2017/9/5.
 * UserService实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ClubDao clubDao;

    @Autowired
    private SauDao sauDao;

    @Override
    public User getUserByUserId(int userId) {
        return userDao.getUserByUserId(userId);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public User getUserByUserNameAndPassword(String userName, String password) {
        return userDao.getUserByUserNameAndPassword(userName, password);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void addNewPerson(User user, Person person) {
        userDao.addUser(user);
        person.setUserId(user.getUserId());
        personDao.addPerson(person);
    }

    @Override
    public void addNewClub(User user, Club club) {
        userDao.addUser(user);
        club.setUserId(user.getUserId());
        clubDao.addClub(club);
    }

    @Override
    public void addNewSau(User user, Sau sau) {
        userDao.addUser(user);
        sau.setUserId(user.getUserId());
        sauDao.addSau(sau);
    }

    @Override
    public void updateUserInfo(User user) {
        userDao.updateUser(user);
    }

    @Override
    public boolean checkSameAccount(String userName) {
        return userDao.hadAccount(userName);
    }

    @Override
    public boolean checkSameEmail(String email) {
        return userDao.hadEmail(email);
    }
}
