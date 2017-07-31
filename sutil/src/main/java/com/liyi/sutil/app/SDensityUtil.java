package com.liyi.sutil.app;

import android.content.Context;
import android.util.TypedValue;

public class SDensityUtil {

    private SDensityUtil() {
        /** cannot be instantiated */
        throw new UnsupportedOperationException("SDensityUtil cannot be instantiated");
    }

    /**
     * Convert dp to px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * Convert sp to px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * Convert px to dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static int px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxVal / scale + 0.5f);
    }

    /**
     * Convert px to dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static int px2sp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxVal / scale + 0.5f);
    }
}
