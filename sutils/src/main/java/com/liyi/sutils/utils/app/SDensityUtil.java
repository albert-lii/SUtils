package com.liyi.sutils.utils.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;


/**
 * 尺寸单位转化工具类
 */
public class SDensityUtil {

    /**
     * dp 转 px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(@NonNull Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp 转 px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(@NonNull Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px 转 dp
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
     * px 转 sp
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
