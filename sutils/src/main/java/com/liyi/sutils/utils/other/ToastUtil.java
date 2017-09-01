package com.liyi.sutils.utils.other;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


/**
 * Toast工具类
 */
public class ToastUtil {
    public static void show(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, @StringRes int stringId) {
        Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, CharSequence msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }

    public static void show(Context context, View root) {
        show(context,root,Toast.LENGTH_SHORT);
    }

    public static void show(Context context, View root, int duration) {
        Toast toast = new Toast(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        toast.setGravity(Gravity.BOTTOM, 0, (int) (height - 2.0 / 3 * height));
        toast.setDuration(duration);
        toast.setView(root);
        toast.show();
    }
}
