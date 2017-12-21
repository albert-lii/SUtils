package com.liyi.sutils.utils.io;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.liyi.sutils.utils.SUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * assets 工具类
 */
public final class AssetUtil {

    /**
     * 获取 assets 目录下的文件
     *
     * @param path 文件在 assets 文件夹中的路径
     * @return 文件内容
     */
    public static String getFileFromAssets(@NonNull String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = SUtils.getApp().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(path)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 获取 assets 目录下的图片
     *
     * @param path 图片在 assets 文件夹中的路径
     * @return
     */
    public static Bitmap getImageFromAssets(@NonNull String path) {
        Bitmap image = null;
        AssetManager am = SUtils.getApp().getResources().getAssets();
        try {
            InputStream is = am.open(path);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
