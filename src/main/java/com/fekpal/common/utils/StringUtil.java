package com.fekpal.common.utils;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 判断是否为空
     * @param string 字符串
     * @return 准确性
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    /**
     * 去掉前后空格
     * @param str 字符串
     * @return 字符串
     */
    public static String trim(String str) {
        return org.apache.commons.lang3.StringUtils.trim(str);
    }
}
