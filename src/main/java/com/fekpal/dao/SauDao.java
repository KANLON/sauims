package com.fekpal.dao;

import com.fekpal.domain.Sau;
import com.fekpal.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by APone on 2017/8/15.
 * SauDao的接口
 */
@Repository
public interface SauDao {

    /**
     * 根据校社联用户id获取校社联信息
     *
     * @param userId int
     * @return Sau
     */
    Sau getSauByUserId(int userId);


    /**
     * 根据校社联id获取校社联
     *
     * @param sauId int
     * @return Sau
     */
    Sau getSauBySauId(int sauId);

    /**
     * 添加校社联
     *
     * @param sau Sau
     */
    void addSau(Sau sau);

    /**
     * 更新校社联信息
     *
     * @param sau Sau
     */
    void updateSau(Sau sau);

    /**
     * 获得所有的校社联
     *
     * @return List
     */
    List<Sau> loadAllSau();
}
