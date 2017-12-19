package com.liyi.sutils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * 二维码相关工具类
 */
public class QRCodeUtil {

    /***********************************************************************************************
     *****  生成二维码
     **********************************************************************************************/

    /**
     * 生成二维码的 Bitmap
     *
     * @param content 二维码中的内容
     * @param width   二维码的宽
     * @param height  二维码的高
     * @return 二维码图片
     */
    public static Bitmap createQRCode(String content, int width, int height) {
        return createQRCode(content, width, height, 2);
    }

    /**
     * 生成二维码的 Bitmap
     *
     * @param content 二维码中的内容
     * @param width   二维码的宽
     * @param height  二维码的高
     * @param height  二维码空白边距的宽度
     * @return 二维码图片
     */
    public static Bitmap createQRCode(String content, int width, int height, int border) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // 配置参数
        Map hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 容错级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置空白边距的宽度，default is 4
        hints.put(EncodeHintType.MARGIN, border);
        try {
            // 图像数据转换，使用了矩阵转换
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个 for 循环是图片横列扫描的结果
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = Color.BLACK;
                    } else {
                        pixels[i * width + j] = Color.WHITE;
                    }
                }
            }
            // 生成二维码图片的格式，使用 ARGB_8888
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 在二维码中间添加 Logo 图案
     *
     * @param qrBitmap   二维码图片
     * @param logoBitmap logo 图片
     * @return 添加了 Logo 的二维码图片
     */
    public static Bitmap addLogoToQRCode(Bitmap qrBitmap, Bitmap logoBitmap) {
        if (qrBitmap == null) return null;
        if (logoBitmap == null) return qrBitmap;
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }


    /***********************************************************************************************
     ****  解析二维码
     **********************************************************************************************/

    /**
     * 解析二维码（使用解析RGB编码数据的方式）
     *
     * @param path 二维码图片所在路径
     * @return 解析结果
     */
    public static Result decodeQRCodeRGB(String path) {
        if (TextUtils.isEmpty(path)) return null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        Bitmap qrcode = BitmapFactory.decodeFile(path, opts);
        Result result = decodeQRCodeRGB(qrcode);
        qrcode.recycle();
        qrcode = null;
        return result;
    }

    /**
     * 解析二维码 （使用解析 RGB 编码数据的方式）
     *
     * @param qrcode 二维码图片
     * @return 解析结果
     */
    public static Result decodeQRCodeRGB(Bitmap qrcode) {
        if (qrcode == null) return null;
        int width = qrcode.getWidth();
        int height = qrcode.getHeight();
        int[] data = new int[width * height];
        qrcode.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(bitmap1);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        qrcode.recycle();
        qrcode = null;
        return result;
    }

    /**
     * 解析二维码（使用解析 YUV 编码数据的方式）
     *
     * @param path 二维码图片所在路径
     * @return 解析结果
     */
    public static Result decodeQRCodeYUV(String path) {
        if (TextUtils.isEmpty(path)) return null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        Bitmap barcode = BitmapFactory.decodeFile(path, opts);
        Result result = decodeQRCodeYUV(barcode);
        barcode.recycle();
        barcode = null;
        return result;
    }

    /**
     * 解析二维码（使用解析 YUV 编码数据的方式）
     *
     * @param qrcode 二维码图片
     * @return 解析结果
     */
    public static Result decodeQRCodeYUV(Bitmap qrcode) {
        if (qrcode == null) return null;
        int width = qrcode.getWidth();
        int height = qrcode.getHeight();
        // 以 argb 方式存放图片的像素
        int[] argb = new int[width * height];
        qrcode.getPixels(argb, 0, width, 0, 0, width, height);
        // 将 argb 转换为 yuv
        byte[] yuv = new byte[width * height * 3 / 2];
        encodeYUV420SP(yuv, argb, width, height);
        // 解析 YUV 编码方式的二维码
        Result result = decodeQRCodeYUV(yuv, width, height);
        qrcode.recycle();
        qrcode = null;
        return result;
    }

    /**
     * 解析二维码（使用解析 YUV 编码数据的方式）
     *
     * @param yuv    二维码图片
     * @param width  二维码图片的宽
     * @param height 二维码图片的高
     * @return 解析结果
     */
    private static Result decodeQRCodeYUV(byte[] yuv, int width, int height) {
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(null);

        Result rawResult = null;
        PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(yuv, width, height, 0, 0,
                width, height, false);
        if (source != null) {
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                rawResult = multiFormatReader.decodeWithState(bitmap);
            } catch (ReaderException re) {
                re.printStackTrace();
            } finally {
                multiFormatReader.reset();
                multiFormatReader = null;
            }
        }
        return rawResult;
    }

    /**
     * RGB 转 YUV 的公式是:
     * Y=0.299R + 0.587G + 0.114B;
     * U=-0.147R - 0.289G + 0.436B;
     * V=0.615R - 0.515G - 0.1B;
     *
     * @param yuv
     * @param argb
     * @param width
     * @param height
     */
    private static void encodeYUV420SP(byte[] yuv, int[] argb, int width, int height) {
        // 帧图片的像素大小
        final int frameSize = width * height;
        // ---YUV 数据---
        int Y, U, V;
        // Y 的 index 从0开始
        int yIndex = 0;
        // UV 的 index 从 frameSize 开始
        int uvIndex = frameSize;
        // ---颜色数据---
        int R, G, B;
        int rgbIndex = 0;
        // ---循环所有像素点，RGB 转 YUV---
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                R = (argb[rgbIndex] & 0xff0000) >> 16;
                G = (argb[rgbIndex] & 0xff00) >> 8;
                B = (argb[rgbIndex] & 0xff);
                //
                rgbIndex++;
                // 已知的 RGB 转 YUV 算法
                Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
                U = ((-38 * R - 74 * G + 112 * B + 128) >> 8) + 128;
                V = ((112 * R - 94 * G - 18 * B + 128) >> 8) + 128;
                Y = Math.max(0, Math.min(Y, 255));
                U = Math.max(0, Math.min(U, 255));
                V = Math.max(0, Math.min(V, 255));
                // NV21 has a plane of Y and interleaved planes of VU each sampled by a factor of 2
                // meaning for every 4 Y pixels there are 1 V and 1 U. Note the sampling is every other
                // pixel AND every other scan line.
                // ---Y---
                yuv[yIndex++] = (byte) Y;
                // ---UV---
                if ((j % 2 == 0) && (i % 2 == 0)) {
                    yuv[uvIndex++] = (byte) V;
                    yuv[uvIndex++] = (byte) U;
                }
            }
        }
    }


    /***********************************************************************************************
     *****  条形码相关
     **********************************************************************************************/

    /**
     * 生成条形码
     *
     * @param contents      需要生成的内容
     * @param desiredWidth  生成条形码的宽度
     * @param desiredHeight 生成条形码的高度
     * @param displayCode   是否在条形码下方显示内容
     * @return
     */
    public static Bitmap createBarCode(String contents, int desiredWidth, int desiredHeight, boolean displayCode) {
        Bitmap ruseltBitmap = null;
        /**
         * 图片两端所保留的空白的宽度
         */
        int marginW = 20;
        /**
         * 条形码的编码类型
         */
        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
        if (displayCode) {
            Bitmap barcodeBitmap = encodeAsBitmap(contents, barcodeFormat,
                    desiredWidth, desiredHeight);
            Bitmap codeBitmap = createCodeBitmap(contents, desiredWidth + 2
                    * marginW, desiredHeight, SUtils.getApp());
            ruseltBitmap = mixtureBitmap(barcodeBitmap, codeBitmap, new PointF(
                    0, desiredHeight));
        } else {
            ruseltBitmap = encodeAsBitmap(contents, barcodeFormat,
                    desiredWidth, desiredHeight);
        }
        return ruseltBitmap;
    }

    /**
     * 生成条形码的 Bitmap
     *
     * @param contents      需要生成的内容
     * @param format        编码格式
     * @param desiredWidth  生成条形码的宽度
     * @param desiredHeight 生成条形码的高度
     * @return
     * @throws WriterException
     */
    private static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int desiredWidth, int desiredHeight) {
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = null;
        try {
            result = writer.encode(contents, format, desiredWidth, desiredHeight, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // 默认情况下，所有的都是 0 或黑色
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 生成显示编码的 Bitmap
     *
     * @param contents
     * @param width
     * @param height
     * @param context
     * @return
     */
    private static Bitmap createCodeBitmap(String contents, int width, int height, Context context) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        tv.setText(contents);
        tv.setHeight(height);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setWidth(width);
        tv.setDrawingCacheEnabled(true);
        tv.setTextColor(Color.BLACK);
        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
        tv.buildDrawingCache();
        Bitmap bitmapCode = tv.getDrawingCache();
        return bitmapCode;
    }

    /**
     * 将两个 Bitmap 合并成一个
     *
     * @param first
     * @param second
     * @param fromPoint 第二个Bitmap开始绘制的起始位置（相对于第一个 Bitmap）
     * @return 合并后的 bitmap
     */
    private static Bitmap mixtureBitmap(Bitmap first, Bitmap second, PointF fromPoint) {
        if (first == null || second == null || fromPoint == null) {
            return null;
        }
        int marginW = 20;
        Bitmap newBitmap = Bitmap.createBitmap(
                first.getWidth() + second.getWidth() + marginW,
                first.getHeight() + second.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas cv = new Canvas(newBitmap);
        cv.drawBitmap(first, marginW, 0, null);
        cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return newBitmap;
    }
}
