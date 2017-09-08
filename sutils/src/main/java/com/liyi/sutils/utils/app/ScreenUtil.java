package com.liyi.sutils.utils.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class ScreenUtil {

    /**
     * 获取屏幕密度dpi（120 / 160 / 240 /...）
     *
     * @param context
     * @return
     */
    public static int getDensityDpi(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        return outMetrics.densityDpi;
    }

    /**
     * 获取屏幕的宽和高
     *
     * @param context
     * @return
     */
    public static Point getScreenSize(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new Point(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    /**
     * 获取屏幕高和宽的比
     *
     * @param context
     * @return
     */
    public static float getScreenRate(@NonNull Context context) {
        Point P = getScreenSize(context);
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取底部导航栏的高度
     *
     * @param context
     * @return
     */
    public static int getNavBarHeight(@NonNull Context context) {
        int result = 0;
        int resourceId = 0;
        // Determines whether the bottom navigation bar is displayed
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 判断是否有底部导航栏
     *
     * @param context
     * @return
     */
    public static boolean hasNavigationBar(@NonNull Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0)
            hasNavigationBar = rs.getBoolean(id);
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if (navBarOverride.equals("1")) {
                hasNavigationBar = false;
            } else if (navBarOverride.equals("0")) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }


    /**
     * 截屏
     *
     * @param activity
     * @return 返回的bitmap包括状态栏
     */
    public static Bitmap screenShot(@NonNull Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Point point = getScreenSize(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, point.x, point.y);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 截屏
     *
     * @param activity
     * @return 返回的bitmap不包括状态栏
     */
    public static Bitmap screenShotNoStatusBar(@NonNull Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Point point = getScreenSize(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, point.x, point.y);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取屏幕方向
     *
     * @return Configuration.ORIENTATION_PORTRAIT、Configuration.ORIENTATION_LANDSCAPE
     */
    public static int getScreenSimpleOrientation(@NonNull Context context) {
        return context.getResources().getConfiguration().orientation;
    }

    /**
     * 获取屏幕方向
     *
     * @param activity
     * @return Surface.ROTATION_0、Surface.ROTATION_90、Surface.ROTATION_180、Surface.ROTATION_270
     */
    public static int getScreenOrientation(@NonNull Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getRotation();
    }

    /**
     * 设置屏幕竖屏
     *
     * @return
     */
    public static void setScreenPortrait(@NonNull Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 设置屏幕横屏
     *
     * @return
     */
    public static void setScreenLandscape(@NonNull Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
