package com.fekpal.common.utils;

import java.time.LocalDate;

/**
 * Created by hasee on 2017/8/18.
 */

public class TimeUtil {

    /**
     * 获取当前年份
     *
     * @return 年份
     */
    public static int getYear() {
        return LocalDate.now().getYear();
    }

    /**
     * 获取当前时间的毫秒级
     *
     * @return 毫秒级
     */
    public static long currentTime() {
        return System.currentTimeMillis();
    }
}