package com.fekpal.common.session;

import com.fekpal.common.base.BaseModel;

/**
 * Created by APone on 2018/1/30 13:05.
 * 存储在session的常用信息类
 */
public class SessionContent {

    /**
     * 生成用户身份实例
     *
     * @return 用户身份封装
     */
    public static UserIdentity createUID() {
        return new UserIdentity();
    }

    /**
     * 生成验证信息实例
     *
     * @return 验证信息封装
     */
    public static Captcha createCaptcha() {
        return new Captcha();
    }

    /**
     * 身份类
     */
    public static class UserIdentity extends BaseModel {

        private static final long serialVersionUID = -2303162431945571719L;

        /**
         * 账号用户标识
         */
        private int accId;

        /**
         * 普通，社团，校社联用户id
         */
        private int uid;

        /**
         * 角色权限，0普通，1，社团，2校社联
         */
        private int auth;

        public int getAccId() {
            return accId;
        }

        public void setAccId(int accId) {
            this.accId = accId;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getAuth() {
            return auth;
        }

        public void setAuth(int auth) {
            this.auth = auth;
        }
    }

    /**
     * 验证信息类
     */
    public static class Captcha extends BaseModel {

        private static final long serialVersionUID = -6418758502209346026L;

        /**
         * 验证码
         */
        private String code;

        /**
         * 有效时间
         */
        private long activeTime;

        /**
         * 创建时间
         */
        private long createTime;

        /**
         * 当前时间
         */
        private long currentTime;

        /**
         * 授权对象
         */
        private String authorize;

        public long getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(long currentTime) {
            this.currentTime = currentTime;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public long getActiveTime() {
            return activeTime;
        }

        public void setActiveTime(long activeTime) {
            this.activeTime = activeTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getAuthorize() {
            return authorize;
        }

        public void setAuthorize(String authorize) {
            this.authorize = authorize;
        }
    }
}
