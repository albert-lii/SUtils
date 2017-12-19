package com.liyi.sutils.utils;

import android.support.annotation.NonNull;

import com.liyi.sutils.constants.RegexCst;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式相关工具类
 */
public final class RegExUtil {

    private RegExUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**************************************************************************
     **** If u want more please visit http://toutiao.com/i6231678548520731137
     **************************************************************************/

    /**
     * 验证手机号（简单）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileSimple(CharSequence input) {
        return isMatch(RegexCst.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 验证手机号（精确）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(CharSequence input) {
        return isMatch(RegexCst.REGEX_MOBILE_EXACT, input);
    }

    /**
     * 验证电话号码
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isTel(CharSequence input) {
        return isMatch(RegexCst.REGEX_TEL, input);
    }

    /**
     * 验证身份证号码 15 位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(CharSequence input) {
        return isMatch(RegexCst.REGEX_ID_CARD15, input);
    }

    /**
     * 验证身份证号码 18 位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(CharSequence input) {
        return isMatch(RegexCst.REGEX_ID_CARD18, input);
    }

    /**
     * 验证邮箱
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(CharSequence input) {
        return isMatch(RegexCst.REGEX_EMAIL, input);
    }

    /**
     * 验证 URL
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isURL(CharSequence input) {
        return isMatch(RegexCst.REGEX_URL, input);
    }

    /**
     * 验证汉字
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isZh(CharSequence input) {
        return isMatch(RegexCst.REGEX_ZH, input);
    }

    /**
     * 验证用户名
     * <p>取值范围为 a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是 6-20 位</p>
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUsername(CharSequence input) {
        return isMatch(RegexCst.REGEX_USERNAME, input);
    }

    /**
     * 验证 yyyy-MM-dd 格式的日期校验，已考虑平闰年
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isDate(CharSequence input) {
        return isMatch(RegexCst.REGEX_DATE, input);
    }

    /**
     * 验证 IP 地址
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIP(CharSequence input) {
        return isMatch(RegexCst.REGEX_IP, input);
    }

    /**
     * 执行正则表达式，判断是否匹配
     *
     * @param regEx 正则表达式
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(@NonNull String regEx, CharSequence input) {
        return input != null && Pattern.matches(regEx, input);
    }

    /**
     * 获取正则匹配的部分
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return 正则匹配的部分
     */
    public static List<String> getMatches(String regex, CharSequence input) {
        if (input == null) return null;
        List<String> matches = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 获取正则匹配分组
     * <p>将字符串分割为给定的匹配项</p>
     *
     * @param regex 正则表达式
     * @param input 要分组的字符串
     * @return 正则匹配分组
     */
    public static String[] getSplits(String regex, String input) {
        if (input == null) return null;
        return input.split(regex);
    }

    /**
     * 替换正则匹配的第一部分
     * <p>替换与给定的替换字符串相匹配的输入序列的第一个子序列</p>
     *
     * @param regex       正则表达式
     * @param input       要替换的字符串
     * @param replacement 代替者
     * @return 被替换的正则匹配的第一部分
     */
    public static String replaceFirst(String regex, String input, String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param regex       正则表达式
     * @param input       要替换的字符串
     * @param replacement 代替者
     * @return 被替换的所有正则匹配的部分
     */
    public static String replaceAll(String regex, String input, String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}
