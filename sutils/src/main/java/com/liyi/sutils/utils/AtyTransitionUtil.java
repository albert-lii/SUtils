package com.liyi.sutils.utils;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.liyi.sutils.R;

/**
 * Activity 过渡动画工具类
 */
public final class AtyTransitionUtil {

    private AtyTransitionUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * Activity 从左边进入
     *
     * @param activity
     */
    public static void enterFromLeft(@NonNull Activity activity) {
        startTransition(activity, R.anim.sutils_left_in, R.anim.sutils_right_out);
    }

    /**
     * Activity 从左边退出
     *
     * @param activity
     */
    public static void exitToLeft(@NonNull Activity activity) {
        startTransition(activity, R.anim.sutils_right_in, R.anim.sutils_left_out);
    }

    /**
     * Activity 从右边进入
     *
     * @param activity
     */
    public static void enterFromRight(@NonNull Activity activity) {
        startTransition(activity, R.anim.sutils_right_in, R.anim.sutils_left_out);
    }

    /**
     * Activity 从右边退出
     *
     * @param activity
     */
    public static void exitToRight(@NonNull Activity activity) {
        startTransition(activity, R.anim.sutils_left_in, R.anim.sutils_right_out);
    }

    /**
     * Activity 从上边进入
     *
     * @param activity
     */
    public static void enterFromTop(@NonNull Activity activity) {
        startTransition(activity, R.anim.sutils_top_in, R.anim.sutils_unchanged);
    }

    /**
     * Activity 从上边退出
     *
     * @param activity
     */
    public static void exitToTop(@NonNull Activity activity) {
        startTransition(activity, R.anim.sutils_unchanged, R.anim.sutils_top_out);
    }

    /**
     * Activity 从下边进入
     *
     * @param activity
     */
    public static void enterFromBottom(@NonNull Activity activity) {
        startTransition(activity, R.anim.sutils_bottom_in, R.anim.sutils_unchanged);
    }

    /**
     * Activity 从下边退出
     *
     * @param activity
     */
    public static void exitToBottom(@NonNull Activity activity) {
        startTransition(activity, R.anim.sutils_unchanged, R.anim.sutils_bottom_out);
    }

    /**
     * 启动过渡动画
     *
     * @param activity   activity
     * @param enterStyle activity 的进场效果样式
     * @param outStyle   activity的退场效果样式
     */
    public static void startTransition(@NonNull Activity activity, int enterStyle, int outStyle) {
        activity.overridePendingTransition(enterStyle, outStyle);
    }
}
