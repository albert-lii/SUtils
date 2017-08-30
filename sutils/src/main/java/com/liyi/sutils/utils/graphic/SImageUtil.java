package com.liyi.sutils.utils.graphic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.support.annotation.NonNull;

import com.liyi.sutils.utils.log.SLogUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SImageUtil {
    private static final String TAG = SImageUtil.class.getSimpleName();

    /**
     * drawbale 转 bitmap
     */
    public static Bitmap drawable2Bitmap(@NonNull Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    /**
     * drawbale 转 bitmap
     */
    private static Bitmap drawable2Bitmap(@NonNull Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * bitmap 转 drawbale
     */
    public static Drawable bitmap2Drawable(@NonNull Bitmap bm) {
        if (bm == null) {
            return null;
        }
        return new BitmapDrawable(bm);
    }

    /**
     * bitmap 转 byte array
     *
     * @param bm
     * @param format 压缩的图片格式：JPEG、PNG、WEBP
     * @return
     */
    public static byte[] bitmap2Byte(@NonNull Bitmap bm, Bitmap.CompressFormat format) {
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Quality compression method, compressed bitmap into jpeg format, 100 indicates uncompressed
        bm.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byte array 转 bitmap
     */
    public static Bitmap byte2Bitmap(byte[] b) {
        if (b == null || b.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * 将图片旋转指定角度
     *
     * @param bm
     * @param degree 旋转的角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(@NonNull Bitmap bm, float degree) {
        Bitmap newbmp = null;
        // 根据旋转角生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 根据旋转矩阵来旋转原始图片，并且获取旋转后的图片
            newbmp = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            SLogUtil.e(TAG, "rotateBitmap error");
        }
        if (newbmp == null) {
            newbmp = bm;
        }
        if (bm != newbmp) {
            bm.recycle();
        }
        return newbmp;
    }

    /**
     * 获取图片需要旋转的角度
     *
     * @param path Image absolute path
     * @return The rotation Angle of the picture
     */
    public static int getBitmapDegree(@NonNull String path) {
        int degree = 0;
        try {
            // 从指定的路径读取图像并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图像的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 获取位图的内存大小
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(@NonNull Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // SDK >= 19
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            // SDK >= 12
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }
}
