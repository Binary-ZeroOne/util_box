package org.zero.utilitybox.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;

/**
 * @ProjectName: backsystem
 * @Author: swang
 * @Date: 2018/8/14 15:18
 * @Description:
 */


public class MD5Util {

    /**
     * 普通MD5
     */
    public static String MD5(String input) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return "check jdk";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = input.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }


    /**
     * 加盐MD5
     */
    public static String generate(String password) {
        int capacity = 16;

        Random r = new Random();
        StringBuilder sb = new StringBuilder(capacity);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < capacity) {
            for (int i = 0; i < capacity - len; i++) {
                sb.append("0");
            }
        }

        int charLength = 48;
        int step = 3;
        String salt = sb.toString();
        password = md5Hex(password + salt);
        if (password == null) {
            throw new RuntimeException("generate fail, password is null");
        }

        char[] cs = new char[charLength];
        for (int i = 0; i < charLength; i += step) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }

        return new String(cs);
    }

    /**
     * 校验加盐后是否和原文一致
     */
    public static boolean verify(String password, String md5) {
        int charLength = 48;
        int step = 3;
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < charLength; i += step) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return Objects.equals(md5Hex(password + salt), new String(cs1));
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    private static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }
}
