package com.liyi.sutils.utils.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.liyi.sutils.SConstants;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class SSystemBarUtil {
    /**
     * 设置状态栏和底部导航栏的显示方式
     */
    public static void setDisplayOption(Activity activity, boolean isFitSystemWindow, boolean clipToPadding) {
        // 获取根布局
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(isFitSystemWindow);
        // true: 内容不会显示到状态栏和导航栏上，false: 内容显示到状态栏和导航栏上
        rootView.setClipToPadding(clipToPadding);
    }

    /**
     * 初始化顶部状态栏
     */
    public static void setupStatusBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusHeight = SScreenUtil.getStatusBarHeight(activity);
            int statusColor = SConstants.DEFAULT_STATUS_COLOR;
            if (color != SConstants.SYSTEMBAR_INVALID_VAL) {
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
     */
    private static View createStatusBarView(Activity activity, int statusHeight, int statusColor) {
        View statusBarView = new View(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(statusColor);
        statusBarView.setTag(SConstants.TAG_STATUS_BAR);
        return statusBarView;
    }

    /**
     * 移除已经存在的 statusBarView
     */
    private static void removeStatusBarView(ViewGroup decorView) {
        View statusBarView = decorView.findViewWithTag(SConstants.TAG_STATUS_BAR);
        if (statusBarView != null) {
            decorView.removeView(statusBarView);
        }
    }

    /**
     * 设置状态栏的透明度
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setStatusBarAlpha(Activity activity, float alpha) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View statusView = decorView.findViewWithTag(SConstants.TAG_STATUS_BAR);
        if (statusView != null) {
            statusView.setAlpha(alpha);
        }
    }

    /**
     * 设置状态栏的显示和隐藏
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void showStatusBar(Activity activity, boolean isShow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View statusView = decorView.findViewWithTag(SConstants.TAG_STATUS_BAR);
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
     * 初始化底部导航栏
     */
    public static void setupNavBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (SScreenUtil.hasNavigationBar(activity)) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                int navHeight = SScreenUtil.getNavBarHeight(activity);
                int navColor = SConstants.DEFAULT_NAVIGATION_COLOR;
                if (color != SConstants.SYSTEMBAR_INVALID_VAL) {
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
     */
    private static View createNavBarView(Activity activity, int navHeight, int navColor) {
        View navBarView = new View(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, navHeight);
        params.gravity = Gravity.BOTTOM;
        navBarView.setLayoutParams(params);
        navBarView.setBackgroundColor(navColor);
        navBarView.setTag(SConstants.TAG_NAVIGATION_BAR);
        return navBarView;
    }

    /**
     * 移除已经存在的 navBarView
     */
    private static void removeNavBarView(ViewGroup decorView) {
        View navBarView = decorView.findViewWithTag(SConstants.TAG_NAVIGATION_BAR);
        if (navBarView != null) {
            decorView.removeView(navBarView);
        }
    }

    /**
     * 设置底部导航栏的透明度
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setNavBarAlpha(Activity activity, float alpha) {
        if (SScreenUtil.hasNavigationBar(activity)) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View navBarView = decorView.findViewWithTag(SConstants.TAG_NAVIGATION_BAR);
            if (navBarView != null) {
                navBarView.setAlpha(alpha);
            }
        }
    }

    /**
     * 设置底部导航栏的显示和隐藏
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void showNavBar(Activity activity, boolean isShow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (SScreenUtil.hasNavigationBar(activity)) {
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
