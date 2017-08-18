package com.liyi.sutils.utils.image;

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

import com.liyi.sutils.utils.prompt.SLogUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SImageUtil {
    private static final String TAG = SImageUtil.class.getSimpleName();

    /**
     * Convert your drawbale to bitmap
     *
     * @return
     */
    public static Bitmap drawable2Bitmap(@NonNull Context context,int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    /**
     * Convert your drawbale to bitmap
     *
     * @param drawable
     * @return
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
     * Convert your bitmap to drawbale
     *
     * @param bm
     * @return
     */
    public static Drawable bitmap2Drawable(@NonNull Bitmap bm) {
        if (bm == null) {
            return null;
        }
        return new BitmapDrawable(bm);
    }

    /**
     * Convert bitmap to byte array
     *
     * @param bm
     * @return
     */
    public static byte[] bitmap2Byte(@NonNull Bitmap bm) {
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Quality compression method, compressed bitmap into jpeg format, 100 indicates uncompressed
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Convert byte array to bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap byte2Bitmap(byte[] b) {
        if (b == null || b.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * Rotate the picture at some Angle
     *
     * @param bm     The picture that need to be rotated
     * @param degree Rotation Angle
     * @return The rotated image
     */
    public static Bitmap rotateBitmap(@NonNull Bitmap bm, float degree) {
        Bitmap newbmp = null;
        // The rotation matrix is generated according to the rotation Angle
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // Rotate the original image according to the rotation matrix and get the new image
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
     * Read the rotation Angle of the image
     *
     * @param path Image absolute path
     * @return The rotation Angle of the picture
     */
    public static int getBitmapDegree(@NonNull String path) {
        int degree = 0;
        try {
            // Read the image from the specified path and get its EXIF information
            ExifInterface exifInterface = new ExifInterface(path);
            // Get the rotation information of the image
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
     * Get the memory size of the bitmap
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(@NonNull Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // The API version is greater than or equal to 19
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            // The API version is greater than or equal to 12
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }
}
