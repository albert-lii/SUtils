package com.liyi.sutils.utils.app;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.liyi.sutils.R;

/**
 * Activity过渡动画工具类
 */

public class AtyTransitionUtil {

    /**
     * Activity 从左边进入
     */
    public static void enterFromLeft(@NonNull Activity activity) {
        startTransition(activity, R.anim.activity_from_left, R.anim.activity_to_right);
    }

    /**
     * Activity 从左边退出
     */
    public static void exitToLeft(@NonNull Activity activity) {
        startTransition(activity, R.anim.activity_from_right, R.anim.activity_to_left);
    }

    /**
     * Activity 从右边进入
     */
    public static void enterFromRight(@NonNull Activity activity) {
        startTransition(activity, R.anim.activity_from_right, R.anim.activity_to_left);
    }

    /**
     * Activity 从右边退出
     */
    public static void exitToRight(@NonNull Activity activity) {
        startTransition(activity, R.anim.activity_from_left, R.anim.activity_to_right);
    }

    /**
     * Activity 从上边进入
     */
    public static void enterFromTop(@NonNull Activity activity) {
        startTransition(activity, R.anim.activity_from_top, R.anim.activity_stay_still);
    }

    /**
     * Activity 从上边退出
     */
    public static void exitToTop(@NonNull Activity activity) {
        startTransition(activity, R.anim.activity_stay_still, R.anim.activity_to_top);
    }

    /**
     * Activity 从下边进入
     */
    public static void enterFromBottom(@NonNull Activity activity) {
        startTransition(activity, R.anim.activity_from_bottom, R.anim.activity_stay_still);
    }

    /**
     * Activity 从下边退出
     */
    public static void exitToBottom(@NonNull Activity activity) {
        startTransition(activity, R.anim.activity_stay_still, R.anim.activity_to_bottom);
    }

    /**
     * 启动过渡动画
     *
     * @param activity
     * @param enterStyle
     * @param outStyle
     */
    public static void startTransition(@NonNull Activity activity, int enterStyle, int outStyle) {
        activity.overridePendingTransition(enterStyle, outStyle);
    }
}
