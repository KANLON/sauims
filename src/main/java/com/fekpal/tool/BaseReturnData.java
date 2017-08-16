package com.fekpal.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来产生返回数据的标准工具类
 * Created by hasee on 2017/8/15.
 */
public class BaseReturnData {

    //创建返回数据的map集合
    private Map<String,Object> map = new HashMap<String, Object>();
    //设置初始默认返回数据
    private int code = 0;
    private String msg = "";
    private Object data = null;

    /**
     *     构造方法
     */
    public BaseReturnData(){
    }
    /**
     * 设置返回数据的状态码和信息
     * @param code  表示返回数据的状态码
     * @param msg   描述返回数据的错误信息，正确时候，则为空
     */
    public void setStateCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * 设置data返回的数据
     * @param object  传递过来返回的object对象
     */
    public void setData(Object object){
        data = object;
        map.put("data",data);
    }

    /**
     * 得到map集合，得到返回的数据
     * @return  返回数据map集合
     */
    public Map getMap(){
        map.put("code",code);
        map.put("msg",msg);
        map.put("data",data);
        return map;
    }

}
