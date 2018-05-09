package com.liyi.sutils.utils;

import android.support.annotation.Nullable;

import com.liyi.sutils.R;

/**
 * 资源相关工具类
 */
public class ResourceUtil {

    /**
     * 根据名称获取 Layout 的资源 Id
     *
     * @param resName Layout 的 xml 的名称
     * @return
     */
    public static int getLayoutByName(@Nullable String resName) {
        return getIdByName("layout", resName);
    }

    /**
     * 根据名称获取控件的资源 Id
     *
     * @param resName 控件的 Id 名称
     * @return
     */
    public static int getWidgetByName(@Nullable String resName) {
        return getIdByName("id", resName);
    }

    /**
     * 根据名称获取 Drawable 的资源 Id
     *
     * @param resName Drawable 的名称
     * @return
     */
    public static int getDrawableByName(@Nullable String resName) {
        return getIdByName("drawable", resName);
    }

    /**
     * 根据名称获取 Color 的资源 Id
     *
     * @param resName Color 的名称
     * @return
     */
    public static int getColorByName(@Nullable String resName) {
        return getIdByName("color", resName);
    }

    /**
     * 根据名称获取 Dimen 的资源 Id
     *
     * @param resName Dimen 的名称
     * @return
     */
    public static int getDimenByName(@Nullable String resName) {
        return getIdByName("dimen", resName);
    }

    /**
     * 根据名称获取 String 的资源 Id
     *
     * @param resName String 的名称
     * @return
     */
    public static int getStringByName(@Nullable String resName) {
        return getIdByName("string", resName);
    }

    /**
     * 根据名称获取 Style 的资源 Id
     *
     * @param resName Style 的名称
     * @return
     */
    public static int getStyleByName(@Nullable String resName) {
        return getIdByName("style", resName);
    }

    /**
     * 根据资源名称获取资源 Id
     *
     * @param className 资源类型
     * @param resName   资源名称
     * @return
     */
    public static int getIdByName(@Nullable String className, @Nullable String resName) {
        String packageName = SUtils.getApp().getPackageName();
        int id = 0;
        try {
            Class r = Class.forName(packageName + ".R");
            Class[] classes = r.getClasses();
            Class desireClass = null;
            for (Class cls : classes) {
                if (cls.getName().split("\\$")[1].equals(className)) {
                    desireClass = cls;
                    break;
                }
            }
            if (desireClass != null) {
                id = desireClass.getField(resName).getInt(desireClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
