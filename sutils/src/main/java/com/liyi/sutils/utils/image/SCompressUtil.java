package com.liyi.sutils.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Image compression utility class
 */

public class SCompressUtil {

    /**
     * Compress the quality of the image
     *
     * @param bmp     Images that need to be compressed
     * @param options Compress the quality of the image（Here, 100 is not compressed）
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
     * Compress the image to the specified size
     *
     * @param bmp  Images that need to be compressed
     * @param size Is less than or equal to the specified image size
     * @return
     */
    public static Bitmap comprsQltyBySize(@NonNull Bitmap bmp, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Here, 100 is not compressed, and the compressed data is stored in the baos
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;
        // The loop determines if the image is larger than size kb after compression, greater than the continue compression
        while (baos.toByteArray().length / 1024 > size) {
            // Reset the baos to empty the baos
            baos.reset();
            // Here, compress the options % and store the compressed data in the baos
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // Go down by 10 every time
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * Compress the images in proportion
     *
     * @param bitmap
     * @param ws     Width compression ratio
     * @param hs     High compression ratio
     * @return
     */
    public static Bitmap comprsScale(@NonNull Bitmap bitmap, float ws, float hs) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        // Scaling by using matrices does not cause memory overflows
        matrix.postScale(ws, hs);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * Calculate the zoom ratio of the image
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
