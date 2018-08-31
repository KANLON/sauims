package com.fekpal.common.utils.msg.phone;

import com.fekpal.common.utils.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机工具类
 */
public class PhoneUtils {

    private static final String regex="";

    private static final Pattern pattern=Pattern.compile(regex);

    public static boolean isValid(String phone){
        if(StringUtil.isEmpty(phone)){
            return false;
        }
        Matcher matcher=pattern.matcher(phone);
        return matcher.matches();
    }
}
