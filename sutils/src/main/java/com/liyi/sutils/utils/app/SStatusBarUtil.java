package com.liyi.sutils.utils.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


public class SStatusBarUtil {
    // 无效的颜色值
    private static final int INVALID_VAL = -1;
    private static final int DEFAULT_STATUS_COLOR = 0x22000000;

    public static void setupStatusBar(Activity activity) {
        setupStatusBar(activity, DEFAULT_STATUS_COLOR);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setupStatusBar(Activity activity, int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int color = DEFAULT_STATUS_COLOR;
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            contentView.setFitsSystemWindows(true);
            if (statusColor != INVALID_VAL) {
                color = statusColor;
            }
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SScreenUtil.getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, lp);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setupNavBar(Activity activity, int navigationColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int color = DEFAULT_STATUS_COLOR;
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            contentView.setFitsSystemWindows(true);
            if (navigationColor != INVALID_VAL) {
                color = navigationColor;
            }
            View navBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SScreenUtil.getNavBarHeight(activity));
            navBarView.setBackgroundColor(color);
            contentView.addView(navBarView, lp);
        }
    }

    public static void applyTranslucentStatusBar(Activity activity) {
        Window window = activity.getWindow();
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        // 4.4以上系统状态栏透明
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
