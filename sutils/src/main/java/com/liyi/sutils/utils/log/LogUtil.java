package com.liyi.sutils.utils.log;

import android.util.Log;


/**
 * Log 相关工具类
 */
public final class LogUtil {
    /* 可以用于 release 时，统一关闭 Log */
    public static boolean isLogEnabled = true;

    /**
     * 打印所有的信息
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (isLogEnabled) {
            Log.v(tag, msg);
        }
    }

    /**
     * 打印 info 信息
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (isLogEnabled) {
            Log.i(tag, msg);
        }
    }

    /**
     * 打印 debug 信息
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (isLogEnabled) {
            Log.d(tag, msg);
        }
    }

    /**
     * 打印警告信息
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (isLogEnabled) {
            Log.w(tag, msg);
        }
    }

    /**
     * 打印错误信息
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (isLogEnabled) {
            Log.e(tag, msg);
        }
    }

    /**
     * 输出 Log 中包含的信息
     *
     * @param stackTraceElement
     * @return Log 中包含的信息
     */
    public static String getLogInfo(StackTraceElement stackTraceElement) {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // 线程ID
        long threadID = Thread.currentThread().getId();
        // 线程名称
        String threadName = Thread.currentThread().getName();
        // 文件名，例如 XXX. Java
        String fileName = stackTraceElement.getFileName();
        // 类名, package name + class name
        String className = stackTraceElement.getClassName();
        // 方法名
        String methodName = stackTraceElement.getMethodName();
        // 输出的log的行数
        int lineNumber = stackTraceElement.getLineNumber();

        logInfoStringBuilder.append("[ ");
        logInfoStringBuilder.append("threadID=" + threadID).append(", ");
        logInfoStringBuilder.append("threadName=" + threadName).append(", ");
        logInfoStringBuilder.append("fileName=" + fileName).append(", ");
        logInfoStringBuilder.append("className=" + className).append(", ");
        logInfoStringBuilder.append("methodName=" + methodName).append(", ");
        logInfoStringBuilder.append("lineNumber=" + lineNumber);
        logInfoStringBuilder.append(" ] ");
        return logInfoStringBuilder.toString();
    }
}
