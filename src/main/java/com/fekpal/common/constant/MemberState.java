package com.fekpal.common.constant;

/**
 * Created by APone on 2018/2/28 2:45.
 * 组织成员状态
 * @author zhangcanlong
 * @since 2019-05-02 修改
 */
public class MemberState {

    /**
     * 审核状态，同时也是在不在部门的状态，0不在，审核不通过
     */
    public static final int LEAVE = 0;

    /**
     * 审核状态，同时也是在不在部门的状态，，1在，审核通过
     */
    public static final int STILL_BEING = 1;
    /**
     * 待审（审核中），适用于社团申请或其他用于标识非审核状态的状态
     */
    public static final int AUDITING = 2;
}
