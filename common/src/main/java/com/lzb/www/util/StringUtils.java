package com.lzb.www.util;

/**
 * 封装字符串常用操作
 *
 * @author lzb
 */
public class StringUtils {
    /**
     * 将目标字符串首字母变为大写
     *
     * @param str 目标字符串
     * @return 首字母变为大写的字符串
     */
    public static String firstCharToUpperCase(String str) {
        return str.toUpperCase().charAt(0) + str.substring(1);
    }

    public static String firstCharToLowerCase(String str) {
        return str.toLowerCase().charAt(0) + str.substring(1);
    }

    public static Integer countStr(String string, char thisChar) {
        char[] charArray = string.toCharArray();
        int count = 0;
        for (char param : charArray) {
            if (param == thisChar) {
                count++;
            }
        }
        return count;
    }

    public static Boolean isNotEmpty(String string) {
        return string != null && !"".equals(string);
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            int charAt = str.charAt(i);
            if (charAt < 48 || charAt > 57) {
                return false;
            }
        }
        return true;
    }

    public static int commaCount(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            int charAt = str.charAt(i);
            if (',' == charAt) {
                count++;
            }
        }
        return count;
    }

    /**
     * 是否是手机号码
     */
    public static boolean isPhone(String phone) {
        if (!StringUtils.isNotEmpty(phone)) {
            return false;
        }
        return phone.matches("^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$");
    }
}
