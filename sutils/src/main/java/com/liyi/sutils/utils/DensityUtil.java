package com.liyi.sutils.utils;

/**
 * 单位转换工具类
 */
public final class DensityUtil {

    private DensityUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp 转 px
     *
     * @param dpVal dp 值
     * @return px 值
     */
    public static int dp2px(float dpVal) {
        final float scale = SUtils.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param spVal
     * @return
     */
    public static int sp2px(float spVal) {
        final float fontScale = SUtils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spVal * fontScale + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param pxVal px 值
     * @return dp 值
     */
    public static int px2dp(float pxVal) {
        final float scale = SUtils.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxVal / scale + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param pxVal px 值
     * @return dp 值
     */
    public static int px2sp(float pxVal) {
        final float scale = SUtils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxVal / scale + 0.5f);
    }
}
