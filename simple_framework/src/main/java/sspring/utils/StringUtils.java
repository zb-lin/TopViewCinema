package sspring.utils;

/**
 * 封装字符串常用操作
 *
 * @author lzb
 */
public class StringUtils {
    public static String firstCharToLowerCase(String str) {
        return str.toLowerCase().charAt(0) + str.substring(1);
    }

}
