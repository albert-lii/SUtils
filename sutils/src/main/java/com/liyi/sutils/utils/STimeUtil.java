package com.liyi.sutils.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.liyi.sutils.utils.prompt.SLogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class STimeUtil {
    private static final String TAG = STimeUtil.class.getClass().getSimpleName();
    private static final String DATE_TYPE = "yyyy-MM-dd HH:mm:ss";

    /**
     * Converts the timestamp to the time string of the specified format
     *
     * @param timeStamp
     * @param dateType
     * @return
     */
    public static String getTimeStr(long timeStamp,@NonNull String dateType) {
        if (TextUtils.isEmpty(dateType)) {
            dateType = DATE_TYPE;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateType);
        return sdf.format(new Date(timeStamp));
    }

    /**
     * Converts the time string to a timestamp
     *
     * @param timeStr
     * @param dateType
     * @return
     */
    public static long getTimeStamp(@NonNull String timeStr, String dateType) {
        if (TextUtils.isEmpty(dateType)) {
            dateType = DATE_TYPE;
        }
        long timeStamp = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(dateType);
        try {
            Date date = sdf.parse(timeStr);
            timeStamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            SLogUtil.e(TAG, "Failure to execute the getTimeStamp () method ========> timeStr: " + timeStr);
        }
        return timeStamp;
    }

    /**
     * Calculate time difference
     *
     * @return
     */
    public static long caculateTimeDiff(@NonNull Object startTime, @NonNull Object endTime, String dateType) {
        if (TextUtils.isEmpty(dateType)) {
            dateType = DATE_TYPE;
        }
        long longStart, longEnd;
        if (startTime instanceof String) {
            longStart = getTimeStamp((String) startTime, dateType);
        } else if (startTime instanceof Long) {
            longStart = (long) startTime;
        } else {
            SLogUtil.e(TAG, "The startTime format error in the getTimeDiffAsSecond () method ========> startTime: " + startTime);
            return -1;
        }
        if (endTime instanceof String) {
            longEnd = getTimeStamp((String) endTime, dateType);
        } else if (endTime instanceof Long) {
            longEnd = (long) endTime;
        } else {
            SLogUtil.e(TAG, "The endTime format error in the getTimeDiffAsSecond () method ========> endTime: " + endTime);
            return -2;
        }
        return (longEnd - longStart);
    }

    /**
     * Calculate time difference
     *
     * @param startTime
     * @param endTime
     * @param dateType
     * @return
     */
    public static int[] caculateTimeDiffArray(@NonNull Object startTime, @NonNull Object endTime, String dateType) {
        long longDiff = caculateTimeDiff(startTime, endTime, dateType) / 1000;
        int days = (int) (longDiff / (60 * 60 * 24));
        int hours = (int) ((longDiff - days * (60 * 60 * 24)) / (60 * 60));
        int minutes = (int) ((longDiff - days * (60 * 60 * 24) - hours * (60 * 60)) / 60);
        int seconds = (int) ((longDiff - days * (60 * 60 * 24) - hours * (60 * 60) - minutes * 60));
        return new int[]{days, hours, minutes, seconds};
    }
}
