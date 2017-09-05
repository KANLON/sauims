package com.fekpal.service;

import com.fekpal.domain.Sau;

import java.util.List;

/**
 * Created by APone on 2017/9/5.
 * SauService接口
 */
public interface SauService {

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
     * 更新校社联信息
     *
     * @param sau Sau
     */
    void updateSauInfo(Sau sau);

    /**
     * 获得所有的校社联
     *
     * @return List
     */
    List<Sau> loadAllSau();
}
