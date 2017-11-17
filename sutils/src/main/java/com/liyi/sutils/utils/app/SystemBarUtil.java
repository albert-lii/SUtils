package com.liyi.sutils.utils.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;


/**
 * 系统状态栏与底部导航栏工具类
 * <p>
 * 仅在 SDK >= 4.4 时有效
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class SystemBarUtil {
    private static final String TAG_STATUS_BAR = "StatusBar";
    private static final String TAG_NAVIGATION_BAR = "NavigationBar";
    /* 无效的颜色值 */
    private static final int INVALID_VAL = -1;
    /* 默认的状态栏颜色 */
    private static final int DEFAULT_STATUS_COLOR = 0x10000000;
    /* 默认的底部导航栏颜色 */
    private static final int DEFAULT_NAVIGATION_COLOR = Color.TRANSPARENT;

    /**
     * 设置状态栏和底部导航栏的显示方式
     *
     * @param activity
     * @param isFitSystemWindow true: 内容不会显示到状态栏和导航栏上，false: 内容显示到状态栏和导航栏上
     * @param clipToPadding
     */
    public static void setDisplayOption(@NonNull Activity activity, boolean isFitSystemWindow, boolean clipToPadding) {
        // 获取根布局
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(isFitSystemWindow);
        rootView.setClipToPadding(clipToPadding);
    }

    /**
     * 设置顶部状态栏
     *
     * @param activity
     * @param color    设置顶部状态栏的颜色
     */
    public static void setupStatusBar(@NonNull Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusHeight = ScreenUtil.getStatusBarHeight(activity);
            int statusColor = DEFAULT_STATUS_COLOR;
            if (color != INVALID_VAL) {
                statusColor = color;
            }
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            // 防止重复添加 statusBarView
            removeStatusBarView(decorView);
            // 绘制一个和状态栏一样高的矩形View
            View statusBarView = createStatusBarView(activity, statusHeight, statusColor);
            // 添加 statusBarView 到整个Window的最顶层布局中,这里的 statusBarView 只是作为状态栏的背景，它的visible不能影响到状态栏的visible
            decorView.addView(statusBarView);
        }
    }

    /**
     * 绘制一个和状态栏一样高的矩形View
     *
     * @param activity
     * @param statusHeight 绘制的矩形状态栏的高度
     * @param statusColor  绘制的矩形状态栏的颜色
     * @return
     */
    private static View createStatusBarView(@NonNull Activity activity, int statusHeight, int statusColor) {
        View statusBarView = new View(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(statusColor);
        statusBarView.setTag(TAG_STATUS_BAR);
        return statusBarView;
    }

    /**
     * 移除已经存在的 statusBarView
     *
     * @param decorView
     */
    private static void removeStatusBarView(@NonNull ViewGroup decorView) {
        View statusBarView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (statusBarView != null) {
            decorView.removeView(statusBarView);
        }
    }

    /**
     * 设置状态栏的透明度
     *
     * @param activity
     * @param alpha    透明度（0-1）
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setStatusBarAlpha(@NonNull Activity activity, float alpha) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View statusView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (statusView != null) {
            statusView.setAlpha(alpha);
        }
    }

    /**
     * 设置状态栏的显示和隐藏
     *
     * @param activity
     * @param isShow   true: 显示  false: 隐藏
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void showStatusBar(@NonNull Activity activity, boolean isShow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View statusView = decorView.findViewWithTag(TAG_STATUS_BAR);
            if (isShow) {
                if (statusView != null) {
                    statusView.setVisibility(View.VISIBLE);
                }
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                if (statusView != null) {
                    statusView.setVisibility(View.GONE);
                }
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
    }

    /**
     * 设置底部导航栏
     *
     * @param activity
     * @param color    底部导航栏的颜色
     */
    public static void setupNavBar(@NonNull Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (ScreenUtil.hasNavigationBar(activity)) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                int navHeight = ScreenUtil.getNavBarHeight(activity);
                int navColor = DEFAULT_NAVIGATION_COLOR;
                if (color != INVALID_VAL) {
                    navColor = color;
                }
                ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                removeNavBarView(decorView);
                View navBarView = createNavBarView(activity, navHeight, navColor);
                decorView.addView(navBarView);
            }
        }
    }

    /**
     * 绘制一个和底部导航栏一样高的矩形View
     *
     * @param activity
     * @param navHeight 绘制的导航栏矩形的高度
     * @param navColor  绘制的导航栏矩形的颜色
     * @return
     */
    private static View createNavBarView(@NonNull Activity activity, int navHeight, int navColor) {
        View navBarView = new View(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, navHeight);
        params.gravity = Gravity.BOTTOM;
        navBarView.setLayoutParams(params);
        navBarView.setBackgroundColor(navColor);
        navBarView.setTag(TAG_NAVIGATION_BAR);
        return navBarView;
    }

    /**
     * 移除已经存在的 navBarView
     *
     * @param decorView
     */
    private static void removeNavBarView(@NonNull ViewGroup decorView) {
        View navBarView = decorView.findViewWithTag(TAG_NAVIGATION_BAR);
        if (navBarView != null) {
            decorView.removeView(navBarView);
        }
    }

    /**
     * 设置底部导航栏的透明度
     *
     * @param activity
     * @param alpha    透明度（0-1）
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setNavBarAlpha(@NonNull Activity activity, float alpha) {
        if (ScreenUtil.hasNavigationBar(activity)) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View navBarView = decorView.findViewWithTag(TAG_NAVIGATION_BAR);
            if (navBarView != null) {
                navBarView.setAlpha(alpha);
            }
        }
    }

    /**
     * 设置底部导航栏的显示和隐藏
     *
     * @param activity
     * @param isShow   true: 显示  false: 隐藏
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void showNavBar(@NonNull Activity activity, boolean isShow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (ScreenUtil.hasNavigationBar(activity)) {
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
}
