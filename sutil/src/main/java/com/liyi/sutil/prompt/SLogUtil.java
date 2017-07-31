package com.liyi.sutil.prompt;

import android.util.Log;

public class SLogUtil {
    private static boolean isLogEnable = true;

    public void setLogEnable(boolean enable) {
        isLogEnable = enable;
    }

    public static void v(String tag, String msg) {
        if (isLogEnable) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isLogEnable) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isLogEnable) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isLogEnable) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isLogEnable) {
            Log.e(tag, msg);
        }
    }

    /**
     * The output log contains information
     */
    public static String getLogInfo(StackTraceElement stackTraceElement) {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // Get the thread ID
        long threadID = Thread.currentThread().getId();
        // Get the name of the thread
        String threadName = Thread.currentThread().getName();
        // Get a filename, such as XXX. Java
        String fileName = stackTraceElement.getFileName();
        // Get the class name, the package name + class name
        String className = stackTraceElement.getClassName();
        // Get the method name
        String methodName = stackTraceElement.getMethodName();
        // Get the number of log output rows
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
