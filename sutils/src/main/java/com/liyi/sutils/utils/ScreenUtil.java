package com.liyi.sutils.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * 屏幕相关工具类
 */
public final class ScreenUtil {
    /* 获取屏幕休眠时长的错误码 */
    public final static int GET_SLEEPTIME_ERROR = -123;

    private ScreenUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取屏幕的宽度和高度（单位：px）
     *
     * @return Point
     */
    public static Point getScreenSize() {
        DisplayMetrics metrics = SUtils.getApp().getResources().getDisplayMetrics();
        return new Point(metrics.widthPixels, metrics.heightPixels);
    }

    /**
     * 获取屏幕的物理尺寸（单位：英寸）
     *
     * @return 物理尺寸
     */
    public static float getScreenSizeOfDevice() {
        DisplayMetrics metrics = SUtils.getApp().getResources().getDisplayMetrics();
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        float w = (width / xdpi) * (width / xdpi);
        float h = (height / ydpi) * (width / xdpi);
        return (float) Math.sqrt(w + h);
    }

    /**
     * 获取屏幕高和宽的比
     *
     * @return 屏幕高和宽的比
     */
    public static float getScreenRatio() {
        Point P = getScreenSize();
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

    /**
     * 获取屏幕密度（指每平方英寸中的像素数）
     * <p>例如：0.75 / 1 / 1.5 / ...（dpi/160 可得）</p>
     *
     * @return 屏幕密度
     */
    public static float getDensity() {
        return SUtils.getApp().getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕像素密度 dpi （指每英寸中的像素数）
     * <p>例如：120 / 160 / 240 /...</p>
     *
     * @return 像素密度 dpi
     */
    public static int getDensityDpi() {
        return SUtils.getApp().getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取状态栏的高度
     *
     * @return 状态栏的高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = SUtils.getApp().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = SUtils.getApp().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取底部导航栏的高度
     *
     * @return 底部导航栏的高度
     */
    public static int getNavBarHeight() {
        int result = 0;
        int resourceId = 0;
        // 判断底部导航栏是否显示
        int rid = SUtils.getApp().getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = SUtils.getApp().getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = SUtils.getApp().getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 判断是否有底部导航栏
     *
     * @return {@code true}: 有底部导航栏<br>{@code false}: 没有底部导航栏
     */
    public static boolean hasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = SUtils.getApp().getResources();
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
     * @return 返回的 bitmap 包括状态栏
     */
    public static Bitmap screenShot(@NonNull Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Point point = getScreenSize();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, point.x, point.y);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 截屏
     *
     * @param activity
     * @return 返回的 bitmap 不包括状态栏
     */
    public static Bitmap screenShotNoStatusBar(@NonNull Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Point point = getScreenSize();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, point.x, point.y);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 设置屏幕为全屏
     * <p>需在 {@code setContentView} 之前调用</p>
     *
     * @param activity activity
     */
    public static void setFullScreen(@NonNull Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置屏幕为横屏
     * <p>还有一种就是在 Activity 中加属性 android:screenOrientation="landscape"</p>
     * <p>不设置 Activity 的 android:configChanges 时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
     * <p>设置 Activity 的 android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
     * <p>设置 Activity 的 android:configChanges="orientation|keyboardHidden|screenSize"（4.0 以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行 onConfigurationChanged 方法</p>
     *
     * @param activity activity
     */
    public static void setLandscape(@NonNull Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLandscape() {
        return SUtils.getApp().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPortrait() {
        return SUtils.getApp().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(@NonNull Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            default:
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
    }

    /**
     * 判断是否锁屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isScreenLock() {
        KeyguardManager km = (KeyguardManager) SUtils.getApp().getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * 设置进入休眠时长
     *
     * @param duration 时长
     */
    public static void setSleepTime(final int duration) {
        Settings.System.putInt(SUtils.getApp().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
    }

    /**
     * 获取进入休眠时长
     *
     * @return 进入休眠时长，报错返回 {@link #GET_SLEEPTIME_ERROR}
     */
    public static int getSleepTime() {
        try {
            return Settings.System.getInt(SUtils.getApp().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return GET_SLEEPTIME_ERROR;
        }
    }

    /**
     * 判断是否是平板
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isTablet() {
        return (SUtils.getApp().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
