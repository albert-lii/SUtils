package com.liyi.sutils.utils.graphic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.liyi.sutils.utils.SUtils;
import com.liyi.sutils.utils.graphic.blur.FastBlur;
import com.liyi.sutils.utils.graphic.blur.RSBlur;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * Image相关工具类
 */
public class ImageUtil {
    private static final String TAG = ImageUtil.class.getSimpleName();

    private ImageUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /***********************************************************************************************
     ****  drawable、bitmap、byte 之间相互转换
     **********************************************************************************************/

    /**
     * drawbale 转 bitmap
     *
     * @param resId drawable 的资源文件 id
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(@DrawableRes int resId) {
        Drawable drawable = ContextCompat.getDrawable(SUtils.getApp(), resId);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * drawbale 转 bitmap
     *
     * @param resId     资源 id
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(@DrawableRes int resId, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        final Resources resources = SUtils.getApp().getResources();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /**
     * drawbale 转 bitmap
     *
     * @param drawable drawable 对象
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * bitmap 转 drawbale
     *
     * @param bitmap bitmap 对象
     * @return drawbale
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(SUtils.getApp().getResources(), bitmap);
    }

    /**
     * bitmap 转 byte array
     *
     * @param bitmap bitmap 对象
     * @param format 压缩的图片格式：JPEG、PNG、WEBP
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat format) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byte array 转 bitmap
     *
     * @param bytes bytes 字节数组
     * @return bitmap
     */
    public static Bitmap bytes2Bitmap(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * drawable 转 byte array
     *
     * @param drawable drawable 对象
     * @param format   格式
     * @return 字节数组
     */
    public static byte[] drawable2Bytes(Drawable drawable, Bitmap.CompressFormat format) {
        return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable), format);
    }

    /**
     * byte array 转 drawable
     *
     * @param bytes 字节数组
     * @return drawable
     */
    public static Drawable bytes2Drawable(byte[] bytes) {
        return bitmap2Drawable(bytes2Bitmap(bytes));
    }

    /**
     * view 转 bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    public static Bitmap view2Bitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    /***********************************************************************************************
     ****  获取 bitmap
     **********************************************************************************************/

    /**
     * 获取 bitmap
     *
     * @param file 文件
     * @return bitmap
     */
    public static Bitmap getBitmap(File file) {
        return file == null ? null : BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    /**
     * 获取 bitmap
     *
     * @param file      文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(File file, int maxWidth, int maxHeight) {
        if (file == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    /**
     * 获取 bitmap
     *
     * @param filePath  文件路径
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(@Nullable String filePath, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取 bitmap
     *
     * @param filePath 文件路径
     * @return bitmap
     */
    public static Bitmap getBitmap(@Nullable String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }

    /**
     * 获取 bitmap
     *
     * @param is 输入流
     * @return bitmap
     */
    public static Bitmap getBitmap(InputStream is) {
        return is == null ? null : BitmapFactory.decodeStream(is);
    }

    /**
     * 计算采样大小
     *
     * @param options   选项
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样大小
     */
    private static int calculateInSampleSize(final BitmapFactory.Options options,
                                             final int maxWidth,
                                             final int maxHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while ((width >>= 1) >= maxWidth && (height >>= 1) >= maxHeight) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

    /**
     * 获取位图的内存大小
     *
     * @param src
     * @return bitmap 的内存大小
     */
    public static int getBitmapSize(Bitmap src) {
        if (src == null) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // SDK >= 19
            return src.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            // SDK >= 12
            return src.getByteCount();
        } else {
            return src.getRowBytes() * src.getHeight();
        }
    }

    /***********************************************************************************************
     ****  图片操作
     **********************************************************************************************/

    /**
     * 裁剪图片
     *
     * @param src    源图片
     * @param x      开始坐标 x
     * @param y      开始坐标 y
     * @param width  裁剪宽度
     * @param height 裁剪高度
     * @return 裁剪后的图片
     */
    public static Bitmap clip(Bitmap src, int x, int y, int width, int height) {
        return src == null ? null : Bitmap.createBitmap(src, x, y, width, height);
    }

    /**
     * 倾斜图片
     *
     * @param src 源图片
     * @param kx  倾斜因子 x
     * @param ky  倾斜因子 y
     * @return 倾斜后的图片
     */
    public static Bitmap skew(Bitmap src, float kx, float ky) {
        return skew(src, kx, ky, 0, 0);
    }

    /**
     * 倾斜图片
     *
     * @param src 源图片
     * @param kx  倾斜因子 x
     * @param ky  倾斜因子 y
     * @param px  平移因子 x
     * @param py  平移因子 y
     * @return 倾斜后的图片
     */
    public static Bitmap skew(Bitmap src, float kx, float ky, float px, float py) {
        if (src == null) {
            return src;
        }
        Matrix matrix = new Matrix();
        matrix.setSkew(kx, ky, px, py);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    /**
     * 旋转图片
     *
     * @param src     源图片
     * @param degrees 旋转角度
     * @param px      旋转点横坐标
     * @param py      旋转点纵坐标
     * @return 旋转后的图片
     */
    public static Bitmap rotate(Bitmap src, int degrees, float px, float py) {
        if (src == null) {
            return null;
        }
        if (degrees == 0) {
            return src;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, px, py);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return ret;
    }

    /**
     * 获取图片旋转角度
     *
     * @param filePath 文件路径
     * @return 旋转角度
     */
    public static int getRotateDegree(@Nullable String filePath) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                default:
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
     * 缩放图片
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @return 缩放后的图片
     */
    public static Bitmap scale(Bitmap src, float scaleWidth, float scaleHeight) {
        if (src == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    /**
     * 缩放图片
     *
     * @param src  源图片
     * @param dsth 缩放后的宽
     * @param dsth 缩放后的高
     * @return 被缩放后的图片
     */
    public static Bitmap scale(Bitmap src, int dstw, int dsth) {
        if (src == null) {
            return null;
        }
        return Bitmap.createScaledBitmap(src, dstw, dsth, true);
    }

    /**
     * 获取图片的缩放比例，保证原图宽高比不变（缩放后的图片宽高完全处于指定的宽高矩阵内，即图片的最长边可显示出来）
     *
     * @param width     原始的宽
     * @param height    原始的高
     * @param reqWidth  缩放后的宽
     * @param reqHeight 缩放后的高
     * @return 缩放比例
     */
    public static float getScaleToFitRatio(int width, int height, int reqWidth, int reqHeight) {
        float ratio = 1.0f;
        final float widthRatio = (float) reqWidth / width;
        final float heightRatio = (float) reqHeight / height;
        ratio = widthRatio < heightRatio ? widthRatio : heightRatio;
        return ratio;
    }

    /**
     * 获取图片的缩放比例，保证原图宽高比不变（缩放后的图片的最短边可显示出来）
     *
     * @param width     原始的宽
     * @param height    原始的高
     * @param reqWidth  缩放后的宽
     * @param reqHeight 缩放后的高
     * @return 缩放比例
     */
    public static float getScaleToFillRatio(int width, int height, int reqWidth, int reqHeight) {
        float ratio = 1.0f;
        final float widthRatio = (float) reqWidth / width;
        final float heightRatio = (float) reqHeight / height;
        ratio = widthRatio < heightRatio ? heightRatio : widthRatio;
        return ratio;
    }

    /***********************************************************************************************
     ****  图片效果处理
     **********************************************************************************************/

    /**
     * 图片去色,返回灰度图片
     *
     * @param src 源图片
     * @return 去色后的图片
     */
    public static Bitmap toGray(Bitmap src) {
        if (src == null) {
            return null;
        }
        int width, height;
        height = src.getHeight();
        width = src.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(src, 0, 0, paint);
        return bmpGrayscale;
    }

    /**
     * 转为圆形图片
     *
     * @param src 源图片
     * @return 圆形图片
     */
    public static Bitmap toRound(Bitmap src) {
        return toRound(src, 0, 0);
    }

    /**
     * 转为圆形图片
     *
     * @param src         源图片
     * @param borderSize  边框尺寸
     * @param borderColor 边框颜色
     * @return 圆形图片
     */
    public static Bitmap toRound(Bitmap src, @IntRange(from = 0) int borderSize, @ColorInt int borderColor) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        int size = Math.min(width, height);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        float center = size / 2f;
        RectF rectF = new RectF(0, 0, width, height);
        rectF.inset((width - size) / 2f, (height - size) / 2f);
        Matrix matrix = new Matrix();
        matrix.setTranslate(rectF.left, rectF.top);
        matrix.preScale((float) size / width, (float) size / height);
        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        Canvas canvas = new Canvas(ret);
        canvas.drawRoundRect(rectF, center, center, paint);
        if (borderSize > 0) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            float radius = center - borderSize / 2f;
            canvas.drawCircle(width / 2f, height / 2f, radius, paint);
        }
        return ret;
    }

    /**
     * 转为圆角图片
     *
     * @param src    源图片
     * @param radius 圆角的度数
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap src, float radius) {
        return toRoundCorner(src, radius, 0, 0);
    }

    /**
     * 转为圆角图片
     *
     * @param src         源图片
     * @param radius      圆角的度数
     * @param borderSize  边框尺寸
     * @param borderColor 边框颜色
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap src, float radius, @IntRange(from = 0) int borderSize, @ColorInt int borderColor) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        Canvas canvas = new Canvas(ret);
        RectF rectF = new RectF(0, 0, width, height);
        float halfBorderSize = borderSize / 2f;
        rectF.inset(halfBorderSize, halfBorderSize);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        if (borderSize > 0) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            paint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawRoundRect(rectF, radius, radius, paint);
        }
        return ret;
    }

    /**
     * 添加文字水印
     *
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标 x
     * @param y        起始坐标 y
     * @return 带有文字水印的图片
     */
    public static Bitmap addTextWatermark(Bitmap src, String content, float textSize, @ColorInt int color,
                                          float x, float y) {
        if (src == null) {
            return null;
        }
        Bitmap ret = src.copy(src.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret);
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
        canvas.drawText(content, x, y + textSize, paint);
        return ret;
    }

    /**
     * 添加图片水印
     *
     * @param src       源图片
     * @param watermark 图片水印
     * @param x         起始坐标 x
     * @param y         起始坐标 y
     * @param alpha     透明度
     * @return 带有图片水印的图片
     */
    public static Bitmap addImageWatermark(Bitmap src, Bitmap watermark,
                                           int x, int y, int alpha) {
        if (src == null) {
            return null;
        }
        Bitmap ret = src.copy(src.getConfig(), true);
        if (watermark != null) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Canvas canvas = new Canvas(ret);
            paint.setAlpha(alpha);
            canvas.drawBitmap(watermark, x, y, paint);
        }
        return ret;
    }

    /**
     * 高斯模糊
     *
     * @param src    源图片
     * @param radius 模糊程度
     * @return 模糊后的图片
     */
    public static Bitmap stackBlurByFast(Bitmap src, int radius) {
        if (src == null) {
            return null;
        }
        return FastBlur.blur(src, radius, false);
    }

    /**
     * 高斯模糊
     *
     * @param src    源图片
     * @param radius 模糊程度
     * @return 模糊后的图片
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap stackBlurByRS(Bitmap src, @IntRange(from = 0, to = 25) int radius) {
        if (src == null) {
            return null;
        }
        return RSBlur.blur(SUtils.getApp(), src, radius);
    }

    /***********************************************************************************************
     ****  图片效果处理
     **********************************************************************************************/

    /**
     * 按缩放压缩
     *
     * @param src       源图片
     * @param newWidth  压缩后的宽度
     * @param newHeight 压缩后的高度
     * @return 缩放压缩后的图片
     */
    public static Bitmap compressByScale(Bitmap src, int newWidth, int newHeight) {
        return scale(src, newWidth, newHeight);
    }

    /**
     * 按缩放压缩
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @return 缩放压缩后的图片
     */
    public static Bitmap compressByScale(Bitmap src, float scaleWidth, float scaleHeight) {
        return scale(src, scaleWidth, scaleHeight);
    }

    /**
     * 按质量压缩
     *
     * @param src     源图片
     * @param format  图片的类型： JPEG、PNG、WEBP
     * @param options 画质压缩范围 0-100，100 表示不压缩
     * @return 被压缩后的图片
     */
    public static Bitmap compressByQuality(Bitmap src, Bitmap.CompressFormat format, @IntRange(from = 0, to = 100) int options) {
        if (src == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(format, options, baos);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 按质量压缩
     *
     * @param src         源图片
     * @param format      图片的类型： JPEG、PNG、WEBP
     * @param maxByteSize 允许图片的最大尺寸
     * @return 被压缩后的图片
     */
    public static Bitmap compressByQuality(Bitmap src, Bitmap.CompressFormat format, long maxByteSize) {
        if (src == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(format, 100, baos);
        byte[] bytes;
        if (baos.size() <= maxByteSize) {
            // 最好质量的不大于最大字节，则返回最佳质量
            bytes = baos.toByteArray();
        } else {
            baos.reset();
            src.compress(format, 0, baos);
            if (baos.size() >= maxByteSize) {
                // 最差质量不小于最大字节，则返回最差质量
                bytes = baos.toByteArray();
            } else {
                // 二分法寻找最佳质量
                int st = 0;
                int end = 100;
                int mid = 0;
                while (st < end) {
                    mid = (st + end) / 2;
                    baos.reset();
                    src.compress(format, mid, baos);
                    int len = baos.size();
                    if (len == maxByteSize) {
                        break;
                    } else if (len > maxByteSize) {
                        end = mid - 1;
                    } else {
                        st = mid + 1;
                    }
                }
                if (end == mid - 1) {
                    baos.reset();
                    src.compress(format, st, baos);
                }
                bytes = baos.toByteArray();
            }
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 按采样大小压缩
     *
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(Bitmap src, int sampleSize) {
        if (src == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 按采样大小压缩
     *
     * @param src       源图片
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(Bitmap src, int maxWidth, int maxHeight) {
        if (src == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }
}
