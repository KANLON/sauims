package com.fekpal.service.impl;

import com.fekpal.dao.SauDao;
import com.fekpal.domain.Sau;
import com.fekpal.service.SauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by APone on 2017/9/5.
 * SauService实现类
 */
@Service
public class SauServiceImpl implements SauService {

    @Autowired
    private SauDao sauDao;

    @Override
    public Sau getSauByUserId(int userId) {
        return sauDao.getSauByUserId(userId);
    }

    @Override
    public Sau getSauBySauId(int sauId) {
        return sauDao.getSauBySauId(sauId);
    }

    @Override
    public void updateSauInfo(Sau sau) {
        sauDao.updateSau(sau);
    }

    @Override
    public List<Sau> loadAllSau() {
        return sauDao.loadAllSau();
    }
}
