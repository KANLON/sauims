package com.fekpal.common.json;


import java.io.Serializable;

/**
 * 用来产生返回数据的标准工具类
 * Created by hasee on 2017/8/15.
 */
public class JsonResult<Result> implements Serializable {

    private static final long serialVersionUID = 8014073413945308268L;

    /**
     * 响应状态
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private Result data;

    /**
     * 构造函数
     */
    public JsonResult() {
        this.code = 0;
        this.msg = "";
        this.data = null;
    }

    /**
     * 设置返回数据的状态码和信息
     *
     * @param code 表示返回数据的状态码
     * @param msg  描述返回数据的错误信息，正确时候，则为空
     */
    public void setStateCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result getData() {
        return data;
    }

    public void setData(Result data) {
        this.data = data;
    }
}
