package com.liyi.sutils.utils.common;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class SToastUtil {
    public static void show(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, @StringRes int stringId) {
        Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, String text, int time) {
        Toast.makeText(context, text, time).show();
    }

    public static void show(Context context, View root) {
        Toast toast = new Toast(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        toast.setGravity(Gravity.BOTTOM, 0, (int) (height - 2.0 / 3 * height));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(root);
        toast.show();
    }
}
