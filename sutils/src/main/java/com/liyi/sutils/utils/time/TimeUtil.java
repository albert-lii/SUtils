package com.liyi.sutils.utils.time;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.liyi.sutils.utils.log.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 时间相关工具类
 */
public final class TimeUtil {
    private static final String TAG = TimeUtil.class.getClass().getSimpleName();
    /* 默认的时间格式 */
    private static final String DEF_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private TimeUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 将时间戳转为时间字符串
     *
     * @param millis 时间戳
     * @return 时间字符串
     */
    public static String millis2String(long millis) {
        return millis2String(millis, DEF_FORMAT);
    }

    /**
     * 将时间戳转为时间字符串
     *
     * @param millis 时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String millis2String(long millis, @Nullable String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(millis));
    }

    /**
     * 将时间字符串转换为时间戳
     *
     * @param timeStr 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Millis(String timeStr) {
        return TextUtils.isEmpty(timeStr) ? 0 : string2Millis(timeStr, DEF_FORMAT);
    }

    /**
     * 将时间字符串转换为时间戳
     *
     * @param timeStr 时间字符串
     * @param format  时间格式
     * @return
     */
    public static long string2Millis(String timeStr, @Nullable String format) {
        if (TextUtils.isEmpty(timeStr)) return 0;
        long millis = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(timeStr);
            millis = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "String2Millis Error ========> timeStr: " + timeStr);
        }
        return millis;
    }

    /**
     * 将时间字符串转为 Date 类型
     *
     * @param timeStr 时间字符串
     * @return Date 类型
     */
    public static Date string2Date(String timeStr) {
        return TextUtils.isEmpty(timeStr) ? null : string2Date(timeStr, DEF_FORMAT);
    }

    /**
     * 将时间字符串转为 Date 类型
     *
     * @param timeStr 时间字符串
     * @param format  时间格式
     * @return Date 类型
     */
    public static Date string2Date(String timeStr, @Nullable String format) {
        if (TextUtils.isEmpty(timeStr)) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 Date 类型转为时间字符串
     *
     * @param date Date 类型时间
     * @return 时间字符串
     */
    public static String date2String(Date date) {
        return date == null ? null : date2String(date, DEF_FORMAT);
    }

    /**
     * 将 Date 类型转为时间字符串
     * <p>格式为 format</p>
     *
     * @param date   Date 类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date date, @Nullable String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将 Date 类型转为时间戳
     *
     * @param date Date 类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(Date date) {
        return date == null ? null : date.getTime();
    }

    /**
     * 将时间戳转为 Date 类型
     *
     * @param millis 毫秒时间戳
     * @return Date 类型时间
     */
    public static Date millis2Date(long millis) {
        return new Date(millis);
    }

    /**
     * 将时间转换天数、小时数、分钟数、秒数
     *
     * @param timeStr 时间字符串
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] millis2Array(String timeStr) {
        return millis2Array(timeStr, DEF_FORMAT);
    }

    /**
     * 将时间转换天数、小时数、分钟数、秒数
     *
     * @param timeStr 时间字符串
     * @param format  时间格式
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] millis2Array(String timeStr, @Nullable String format) {
        return TextUtils.isEmpty(timeStr) ? null : millis2Array(string2Millis(timeStr));
    }

    /**
     * 将时间转换天数、小时数、分钟数、秒数
     *
     * @param date 时间
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] millis2Array(Date date) {
        return date == null ? null : millis2Array(date.getTime());
    }

    /**
     * 将时间转换天数、小时数、分钟数、秒数
     *
     * @param millis 毫秒数
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] millis2Array(long millis) {
        long secondDiff = millis / 1000;
        int days = (int) (secondDiff / (60 * 60 * 24));
        int hours = (int) ((secondDiff - days * (60 * 60 * 24)) / (60 * 60));
        int minutes = (int) ((secondDiff - days * (60 * 60 * 24) - hours * (60 * 60)) / 60);
        int seconds = (int) ((secondDiff - days * (60 * 60 * 24) - hours * (60 * 60) - minutes * 60));
        return new int[]{days, hours, minutes, seconds};
    }

    /**
     * 计算时间差
     *
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return 毫秒级时间差
     */
    public static long caculateTimeDiff(@NonNull Object startTime, @NonNull Object endTime) {
        return caculateTimeDiff(startTime, endTime, DEF_FORMAT);
    }

    /**
     * 计算时间差
     *
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @param foramt    时间格式
     * @return 毫秒级时间差
     */
    public static long caculateTimeDiff(@NonNull Object startTime, @NonNull Object endTime, @Nullable String foramt) {
        long milliStart, milliEnd;
        if (startTime instanceof String) {
            milliStart = string2Millis((String) startTime, foramt);
        } else if (startTime instanceof Long) {
            milliStart = (long) startTime;
        } else if (startTime instanceof Date) {
            milliStart = ((Date) startTime).getTime();
        } else {
            LogUtil.e(TAG, "Error startTime in the caculateTimeDiff () method ========> startTime: " + startTime);
            throw new UnsupportedOperationException("startTime foramt error");
        }
        if (endTime instanceof String) {
            milliEnd = string2Millis((String) endTime, foramt);
        } else if (endTime instanceof Long) {
            milliEnd = (long) endTime;
        } else if (endTime instanceof Date) {
            milliEnd = ((Date) endTime).getTime();
        } else {
            LogUtil.e(TAG, "Error endTime in the caculateTimeDiff () method ========> endTime: " + endTime);
            throw new UnsupportedOperationException("endTime foramt error");
        }
        return (milliEnd - milliStart);
    }

    /**
     * 计算时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] caculateTimeDiffArray(@NonNull Object startTime, @NonNull Object endTime) {
        return caculateTimeDiffArray(startTime, endTime);
    }

    /**
     * 计算时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    时间格式
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] caculateTimeDiffArray(@NonNull Object startTime, @NonNull Object endTime, @Nullable String format) {
        return millis2Array(caculateTimeDiff(startTime, endTime, format));
    }

    /**
     * 比较两个时间的大小
     *
     * @param t1 时间 1
     * @param t2 时间 2
     * @return {@code true}: t1 >= t2<br>{@code false}: t1 < t2
     */
    public static boolean judgeTime(@NonNull Object t1, @NonNull Object t2) {
        return caculateTimeDiff(t2, t1) >= 0;
    }
}
