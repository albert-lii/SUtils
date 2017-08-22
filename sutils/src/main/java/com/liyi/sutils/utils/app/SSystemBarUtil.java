package com.liyi.sutils.utils.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class SSystemBarUtil {
    // 无效值
    public static final int INVALID_VAL = -1;
    // 默认的状态栏的颜色
    private static final int DEFAULT_STATUS_COLOR = 0x10000000;

    public static void setupStatusBar(Activity activity) {
        setupStatusBar(activity, INVALID_VAL, false);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setupStatusBar(Activity activity, int color, boolean isFitSystemWindow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusHeight = SScreenUtil.getStatusBarHeight(activity);
            int statusColor = DEFAULT_STATUS_COLOR;
            if (color != INVALID_VAL) {
                statusColor = color;
            }
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 绘制一个和状态栏一样高的矩形View
            View statusView = new View(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusHeight);
            params.gravity = Gravity.TOP;
            statusView.setLayoutParams(params);
            statusView.setBackgroundColor(statusColor);
            // 添加 statusView 到整个Window的最顶层布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            if (isFitSystemWindow) {
                // 获取根布局
                ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
                // 如果不设置该参数，会使内容显示到状态栏上
                rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(false);
            }
        }
    }

    public static void removeStatusBar(){

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void showNavBar(Activity activity, boolean isShow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            View decorView = activity.getWindow().getDecorView();
            if (isShow) {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            } else {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }
}
