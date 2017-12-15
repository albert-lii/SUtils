package com.liyi.sutils.utils.graphic.blur;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.IntRange;


/**
 * 调用 Android 自己的 RenderScript 实现图片模糊
 * 注：仅在 SDK >= 17 时有用
 */
public class RSBlur {
    /**
     * @param context       上下文对象
     * @param blurredBitmap 待模糊图片
     * @param radius        模糊度（0-25）
     * @return 被模糊后的图片
     * @throws RSRuntimeException
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blur(Context context, Bitmap blurredBitmap, @IntRange(from = 0, to = 25) int radius) throws RSRuntimeException {
        try {
            RenderScript rs = RenderScript.create(context);
            Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs, input.getType());
            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            blur.setInput(input);
            blur.setRadius(radius);
            blur.forEach(output);
            output.copyTo(blurredBitmap);
            rs.destroy();
        } catch (RSRuntimeException e) {
            blurredBitmap = FastBlur.blur(blurredBitmap, radius, true);
        }
        return blurredBitmap;
    }
}
