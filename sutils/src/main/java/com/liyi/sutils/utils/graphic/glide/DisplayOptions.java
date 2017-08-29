package com.liyi.sutils.utils.graphic.glide;


import android.content.Context;

import com.bumptech.glide.request.RequestOptions;
import com.liyi.sutils.utils.graphic.glide.transform.CircleTransform;
import com.liyi.sutils.utils.graphic.glide.transform.RoundTransform;
import com.liyi.sutils.utils.graphic.glide.transform.blur.BlurTransformation;

public class DisplayOptions {
    /**
     * 圆形
     */
    public static RequestOptions circleOptions(Context context) {
        RequestOptions options = new RequestOptions();
        options.transform(new CircleTransform(context));
        return options;
    }

    /**
     * 圆角
     */
    public static RequestOptions roundOptions(Context context, int radius) {
        RequestOptions options = new RequestOptions();
        options.transform(new RoundTransform(context, radius));
        return options;
    }

    /**
     * 高斯模糊
     */
    public static RequestOptions blurOptions(Context context, int radius) {
        RequestOptions options = new RequestOptions();
        options.transform(new BlurTransformation(context, radius));
        return options;
    }
}
