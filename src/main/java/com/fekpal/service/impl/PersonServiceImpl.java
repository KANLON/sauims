package com.fekpal.service.impl;

import com.fekpal.dao.LikeClubDao;
import com.fekpal.dao.PersonDao;
import com.fekpal.domain.Person;
import com.fekpal.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by APone on 2017/9/5.
 * PersonService实现类
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private LikeClubDao likeClubDao;

    @Override
    public Person getPersonByPersonId(int personId) {
        return personDao.getPersonByPersonId(personId);
    }

    @Override
    public Person getPersonByNickName(String nickName) {
        return personDao.getPersonByNickName(nickName);
    }

    @Override
    public Person getPersonByUserId(int userId) {
        return personDao.getPersonByUserId(userId);
    }

    @Override
    public boolean checkSameNickName(String nickName) {
        return personDao.hadNickName(nickName);
    }

    @Override
    public void updatePerson(Person person) {
        personDao.updatePerson(person);
    }

    @Override
    public void addLikeClub(int personId, int clubId) {
        likeClubDao.addLikeClub(personId, clubId);
    }

    @Override
    public List<Integer> loadAllLikeByPersonId(int personId) {
        return likeClubDao.loadAllLikeByPersonId(personId);
    }

    @Override
    public List<Person> loadAllPerson(int start, int count) {
        return personDao.loadAllPerson(start, count);
    }
}
