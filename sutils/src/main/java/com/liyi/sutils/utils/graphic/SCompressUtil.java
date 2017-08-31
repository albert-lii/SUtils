package com.liyi.sutils.utils.graphic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * bitmap压缩工具类
 */
public class SCompressUtil {

    /**
     * 质量压缩
     *
     * @param bmp
     * @param options 范围0-100，100表示不压缩
     * @return
     */
    public static Bitmap comprsQltyByoptions(@NonNull Bitmap bmp, int options) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 质量压缩，并且指定压缩后图片的大小
     *
     * @param bmp  I
     * @param size 指定压缩后的大小（这里实际图片的大小可能小于或者等于指定的大小）
     * @return
     */
    public static Bitmap comprsQltyBySize(@NonNull Bitmap bmp, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;
        // 循环判断图片的大小是否大于指定的大小
        while (baos.toByteArray().length / 1024 > size) {
            // 充值baos来达到清空baos中数据的目的
            baos.reset();
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次图片的质量都减少10%
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 按比例压缩
     *
     * @param bitmap
     * @param ws     宽的缩放比例
     * @param hs     高的缩放比例
     * @return
     */
    public static Bitmap comprsScale(@NonNull Bitmap bitmap, float ws, float hs) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        // 用矩阵进行缩放，防止出现OOM
        matrix.postScale(ws, hs);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * 计算图片缩放的比例
     *
     * @param width
     * @param height
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static float getRatioRate(int width, int height, int reqWidth, int reqHeight) {
        float ratio = 1.0f;
        if (width > reqWidth || height > reqHeight) {
            final float widthRatio = (float) reqWidth / width;
            final float heightRatio = (float) reqHeight / height;
            ratio = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return ratio;
    }
}
