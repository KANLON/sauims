package com.fekpal.common.utils;

import sun.misc.BASE64Encoder;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by APone on 2018/2/9 2:27.
 * 随机数工具
 */
public class RandomUtil {


    private static final char[] base =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                    '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '_'};


    /**
     * 生成盐
     *
     * @return 盐
     */
    public static String createSalt() {
        Random RANDOM = new SecureRandom();
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return new BASE64Encoder().encode(salt);
    }

    /**
     * 生成随机文件名
     *
     * @return 随机文件名
     */
    public static String createFileName(int fileNameLength) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(base[random.nextInt(26)]);

        for (int i = 0; i < fileNameLength - 2; i++) {
            int num = random.nextInt(base.length);
            sb.append(base[num]);
        }
        //追加最后一个数字
        sb.append(base[random.nextInt(10) + 42]);
        return sb.toString();
    }

    /**
     * 生成随机文件名,长度为20位
     *
     * @return 随机文件名
     */
    public static String createFileName() {
        return createFileName(20);
    }

    public static void main(String[] args) throws Exception {
        String salt=createSalt();
        System.out.println(salt);
        System.out.println(MD5Util.md5("123456"+salt));

    }
}
