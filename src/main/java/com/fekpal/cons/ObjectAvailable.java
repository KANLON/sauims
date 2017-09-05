package com.fekpal.cons;

/**
 * Created by APone on 2017/8/15.
 * 状态常量
 */
public class ObjectAvailable {

    /**
     * 无效的
     */
    public static final int UNAVAIABLE = 0;

    /**
     * 有效的
     */
    public static final int AVAILABLE = 1;

    /**
     * 待审（审核中），适用于社团申请或其他用于标识非审核状态的状态
     */
    public static final int AUDITING = 2;
}
