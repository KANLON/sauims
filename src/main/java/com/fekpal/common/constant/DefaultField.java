package com.fekpal.common.constant;

import java.sql.Timestamp;

/**
 * Created by APone on 2018/2/9 2:23.
 */
public class DefaultField {

    /**
     * 默认字符为无
     */
    public static final String EMPTY = "无";

    /**
     * 默认手机号码，为空但不为null
     */
    public static final String DEFAULT_PHONE = "";

    /**
     * 默认头像图片文件名
     */
    public static final String DEFAULT_LOGO = "default_logo.jpg";

    /**
     * 默认性别，性别男
     */
    public static final int DEFAULT_GENDER = 0;

    /**
     * 默认昵称前缀，后面可以加其他标识
     */
    public static final String DEFAULT_NICKNAME = "sauims_";

    /**
     * 默认人数
     */
    public static final int DEFAULT_MEMBERS = 0;

    /**
     * 默认社团预览图问文件名（展示图）
     */
    public static final String DEFAULT_CLUB_OVERVIEW = "default_overview.png";

    /**
     * 默认时间，以1970年开始
     */
    public static final Timestamp DEFAULT_TIME = new Timestamp(0);
}
