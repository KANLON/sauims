package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.Org;
import com.fekpal.service.model.domain.SauMsg;

/**
 * Created by APone on 2017/9/5.
 * 校社联用户信息接口
 * 该接口主要提功校社联用户信息增删查改操作
 * @author APone
 * @date 2017/9/5
 */
public interface SauService extends BaseService<Org> {

    /**
     * 根据校社联用户标识获得个人
     *
     * @return 校社联用户记录
     */
    Org selectByPrimaryId();

    /**
     * 根据校社联用户标识更新校社联头像
     *
     * @param msg 校社联修改信息封装
     *            传入参数：头像文件logo
     * @return 头像名
     */
    String updateLogo(SauMsg msg);

    /**
     * 根据校社联用户标识更新校社联展示
     *
     * @param msg 校社联修改信息封装
     *            传入参数：头像文件logo
     * @return 头像名
     */
    String updateView(SauMsg msg);

    /**
     * 是否有相同的校社联名称的校社联
     *
     * @param name 校社联名称
     * @return 是否存在
     */
    boolean isExitSauName(String name);

    /**
     * 更新校社联用户信息
     *
     * @param msg 校社联修改信息封装
     *            传入参数：
     * @return 更新状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败 Operation.INPUT_INCORRECT 存在相同校社联名称
     */
    int updateSauInfo(SauMsg msg);

}
