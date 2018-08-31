package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.LikeOrg;
import com.fekpal.dao.model.OrgMember;

import java.util.List;

/**
 * Created by zhangcanlong on 2018/3/22.
 * 喜爱社团的接口
 * 该接口提供社团点赞，取消点赞等功能
 */
public interface LikeOrgService extends BaseService<LikeOrg> {
    /**
     * 根据根据社团id和用户自己的查看该社团的点赞状态
     *
     * @param orgId 社团id
     * @return 喜爱社团记录
     */
    LikeOrg selectByOrgId(int orgId);

    /**
     * 取消该用户对某社团的点赞
     *
     * @param orgId 社团id
     * @return 取消状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int cancelLikeById(int orgId);

    /**
     * 该用户对某社团的点赞
     *
     * @param orgId 社团id
     * @return 点赞 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int likeById(int orgId);

    /**
     * 根据社团id和他自身的点赞状态，来决定该用户对某社团是点赞还是取消点赞
     *
     * @param orgId 社团id
     * @return 点赞 Operation.SUCCESSFULLY 成功 Operation.CANCEL;取消点赞成功 Operation.FAILED 操作失败
     */
    int likeByOrgIdAndState(int orgId);
}
