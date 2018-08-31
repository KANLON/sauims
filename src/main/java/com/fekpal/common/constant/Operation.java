package com.fekpal.common.constant;

/**
 * Created by APone on 2018/2/7 1:25.
 * 操作状态，常用于与服务层部分服务操作返回状态
 * 需要用时会注释出
 */
public class Operation {

    /**
     * 操作失败常量
     */
    public static final int FAILED = 0;

    /**
     * 操作成功常量
     */
    public static final int SUCCESSFULLY = 1;

    /**
     * 验证码错误常量
     */
    public static final int CAPTCHA_INCORRECT = 2;

    /**
     * 输入错误、为空、不符合要求
     */
    public static final int INPUT_INCORRECT = 3;

    /**
     * 操作取消
     */
    public static final int CANCEL = 4;
}
