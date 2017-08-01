package com.liyi.sutil.utils.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class SScreenUtil {

    /**
     * Get screen width and height
     *
     * @param context
     * @return
     */
    public static Point getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new Point(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    /**
     * Get the ratio of screen height to width
     *
     * @param context
     * @return
     */
    public static float getScreenRate(Context context) {
        Point P = getScreenSize(context);
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

    /**
     * Get the height of the status bar
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Determine whether there is a NavigationBar (bottom navigation bar)
     *
     * @param context
     * @return
     */
    public static boolean isHasNavigationBar(Context context) {
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
     * Get the current screen capture, including the status bar
     *
     * @param activity
     * @return
     */
    public static Bitmap screenShot(Activity activity) {
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
     * Get the current screen capture, not including the status bar
     *
     * @param activity
     * @return
     */
    public static Bitmap screenShotNoStatusBar(Activity activity) {
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
}
