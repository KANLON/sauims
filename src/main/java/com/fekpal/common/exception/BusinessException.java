package com.fekpal.common.exception;

/**
 * 自定义的service层异常类
 * @author zhangcanlong
 * @date 2018/8/19
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException(){
    }
    public BusinessException(String message){
        super(message);
    }
    public BusinessException(String message,Throwable cause){
        super(message,cause);
    }
    public BusinessException(Throwable cause){
        super(cause);
    }

}
