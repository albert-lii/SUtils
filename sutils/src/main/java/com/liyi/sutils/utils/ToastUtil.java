package com.liyi.sutils.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


/**
 * Toast工具类
 */
public class ToastUtil {
    /**
     * @param context
     * @param msg     提示内容
     */
    public static void show(@NonNull Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param context
     * @param stringId 提示内容的id
     */
    public static void show(@NonNull Context context, @StringRes int stringId) {
        Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param context
     * @param msg      提示内容
     * @param duration 提示显示时间
     */
    public static void show(@NonNull Context context, CharSequence msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }

    /**
     * @param context
     * @param root    提示内容的布局
     */
    public static void show(@NonNull Context context, View root) {
        show(context, root, Toast.LENGTH_SHORT);
    }

    /**
     * @param context
     * @param root     提示内容的布局
     * @param duration 提示显示的时间
     */
    public static void show(@NonNull Context context, View root, int duration) {
        Toast toast = new Toast(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        toast.setGravity(Gravity.BOTTOM, 0, (int) (height - 2.0 / 3 * height));
        toast.setDuration(duration);
        toast.setView(root);
        toast.show();
    }
}
