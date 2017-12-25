package com.liyi.sutils.utils;

import android.text.TextUtils;

import com.liyi.sutils.constant.RegexCst;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 相关工具类
 */
public final class StringUtil {

    private StringUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 首字母大写
     * <p>进行字母的 ASCII 编码前移，更加高效</p>
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String toUpperFristChar(String s) {
        if (TextUtils.isEmpty(s) || Character.isUpperCase(s.charAt(0))) return s;
//        s = s.substring(0, 1).toUpperCase() + s.substring(1);
//        return s;
        // 效率更高
        char[] charArray = s.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (TextUtils.isEmpty(s) || Character.isLowerCase(s.charAt(0))) return s;
//        s = s.substring(0, 1).toLowerCase() + s.substring(1);
//        return s;
        char[] charArray = s.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }

    /**
     * 判断字符串是否是数字
     *
     * @param s 字符串
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isNumeric(String s) {
        Pattern pattern = Pattern.compile(RegexCst.REGEX_NUMBER);
        Matcher isNum = pattern.matcher(s);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 检查字符串长度，如果字符串的长度超过 maxLength，就截取前 maxLength 个字符串并在末尾拼上 …
     *
     * @param string    字符串
     * @param maxLength 最大长度
     * @return 处理后的字符串
     */
    public static String checkLength(String string, int maxLength) {
        return checkLength(string, maxLength, "…");
    }

    /**
     * 检查字符串长度，如果字符串的长度超过 maxLength，就截取前 maxLength 个字符并在末尾拼上 appendString
     *
     * @param string       字符串
     * @param maxLength    最大长度
     * @param appendString 末尾拼加的 string
     * @return 处理后的字符串
     */
    public static String checkLength(String string, int maxLength, String appendString) {
        if (string != null && string.length() > maxLength) {
            string = string.substring(0, maxLength);
            if (appendString != null) {
                string += appendString;
            }
        }
        return string;
    }

    /**
     * 删除给定字符串中给定位置处的字符
     *
     * @param string 给定字符串
     * @param index  给定位置
     * @return 处理后的字符串
     */
    public static String removeChar(String string, int index) {
        String result = null;
        char[] chars = string.toCharArray();
        if (index == 0) {
            result = new String(chars, 1, chars.length - 1);
        } else if (index == chars.length - 1) {
            result = new String(chars, 0, chars.length - 1);
        } else {
            result = new String(chars, 0, index) +
                    new String(chars, index + 1, chars.length - index);
        }
        return result;
    }
}
