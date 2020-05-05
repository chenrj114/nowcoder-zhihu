package com.chenrj.zhihu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName SecurityUtil
 * @Description
 * @Author rjchen
 * @Date 2020-05-04 22:03
 * @Version 1.0
 */
public class SecurityUtil {

    public static String MD5(String key) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        byte[] keyByte = key.getBytes();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(keyByte);
            byte[] digest = md5.digest();
            char[] cryptograph = new char[digest.length*2];
            for (int i = 0, k = 0; i < digest.length; i++) {
                byte digit = digest[i];
                cryptograph[k++] = hexDigit[digit >>> 4 & 0xf];
                cryptograph[k++] = hexDigit[digit & 0xf];
            }
            return new String(cryptograph);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
