package com.liyi.sutils.utils.graphic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.support.annotation.NonNull;

import com.liyi.sutils.utils.log.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Image相关工具类
 */
public class ImageUtil {
    private static final String TAG = ImageUtil.class.getSimpleName();

    /***********************************************************************************************
     ****  drawable、bitmap、byte之间相互转换
     **********************************************************************************************/

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
     * @return 字节数组
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

    /***********************************************************************************************
     ****  图片处理
     **********************************************************************************************/

    /**
     * 将图片旋转指定角度
     *
     * @param source
     * @param degree 旋转的角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(@NonNull Bitmap source, float degree) {
        Bitmap newbmp = null;
        // 根据旋转角生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 根据旋转矩阵来旋转原始图片，并且获取旋转后的图片
            newbmp = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            LogUtil.e(TAG, "rotateBitmap error");
        }
        if (newbmp == null) {
            newbmp = source;
        }
        if (source != newbmp) {
            source.recycle();
        }
        return newbmp;
    }

    /**
     * 获取图片需要旋转的角度
     *
     * @param path 图片的绝对路径
     * @return 图片的旋转角度
     */
    public static int getImageDegree(@NonNull String path) {
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
     * @param source
     * @return
     */
    public static int getBitmapSize(@NonNull Bitmap source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // SDK >= 19
            return source.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            // SDK >= 12
            return source.getByteCount();
        } else {
            return source.getRowBytes() * source.getHeight();
        }
    }

    /**
     * bitmap截图
     *
     * @param source 原图
     * @param x      矩形截取区域左上角的X轴坐标（即起点的X坐标）
     * @param y      矩形截取区域左上角的Y轴坐标（即起点的Y坐标）
     * @param width  截取的图片的宽
     * @param height 截取的图片的高
     * @return 截取后的图片
     */
    public static Bitmap cutBitmap(Bitmap source, int x, int y, int width, int height) {
        return Bitmap.createBitmap(source, x, y, width, height);
    }

    /**
     * 图片去色,返回灰度图片
     *
     * @param source 传入的图片
     * @return 去色后的图片
     */
    public static Bitmap toGrayscale(Bitmap source) {
        int width, height;
        height = source.getHeight();
        width = source.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(source, 0, 0, paint);
        return bmpGrayscale;
    }
}
