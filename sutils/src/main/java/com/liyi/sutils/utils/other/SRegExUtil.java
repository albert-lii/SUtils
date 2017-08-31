package com.liyi.sutils.utils.other;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */

public class SRegExUtil {

    public static boolean compile(@NonNull String regEx, CharSequence value) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
