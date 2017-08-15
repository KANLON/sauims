package com.fekpal.tool;

/**
 * 字符串的工具类
 * Created by hasee on 2017/8/15.
 */
public class StringUtils {
    /**
     * 用来判断一个字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str==null || "".equals(str.trim())) {
            return true;
        }else{
            return false;
        }
    }
}
