package com.fekpal.common.utils.captcha;

/**
 * Created by APone on 2018/2/11 16:57.
 * 验证码工具类
 */
public class CaptchaUtil {

    /**
     * 创建新的验证码实例
     *
     * @return 验证码信息
     */
    public static Captcha create() {
        return new Captcha();
    }
}
