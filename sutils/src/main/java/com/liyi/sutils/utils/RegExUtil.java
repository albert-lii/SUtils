package com.liyi.sutils.utils;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class RegExUtil {

    /**
     * 执行正则表达式，判断是否匹配
     *
     * @param regEx 正则表达式
     * @param value 被匹配的字符序列
     * @return true: 与正则表达式匹配  false: 与正则表达式不匹配
     */
    public static boolean compile(@NonNull String regEx, CharSequence value) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
