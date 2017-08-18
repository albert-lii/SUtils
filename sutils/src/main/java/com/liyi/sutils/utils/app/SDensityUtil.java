package com.liyi.sutils.utils.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;

public class SDensityUtil {

    /**
     * Convert dp to px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(@NonNull Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * Convert sp to px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(@NonNull Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * Convert px to dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static int px2dp(@NonNull Context context, float pxVal) {
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
    public static int px2sp(@NonNull Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxVal / scale + 0.5f);
    }
}
