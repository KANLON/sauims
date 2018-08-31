package com.fekpal.common.base;

/**
 * Created by APone on 2018/2/6 0:36.
 * 用于事务出现异常回滚的抛出自定义异常
 */
public class CRUDException extends RuntimeException {

    private static final long serialVersionUID = 9182577027449098462L;

    public CRUDException() {
    }

    public CRUDException(String message) {
        super(message);
    }
}
