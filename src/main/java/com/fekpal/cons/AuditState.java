package com.fekpal.cons;

/**
 * Created by APone on 2017/8/23.
 * 审核等状态
 */
public class AuditState {

    /**
     * 否决
     */
    public static final int REJECT = 0;

    /**
     * 通过
     */
    public static final int PASS = 1;

    /**
     * 待审（审核中）
     */
    public static final int AUDITING = 2;
}
