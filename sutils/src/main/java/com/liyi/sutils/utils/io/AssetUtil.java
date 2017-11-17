package com.liyi.sutils.utils.io;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * assets工具类
 */
public class AssetUtil {

    /**
     * 获取assets目录下的文件
     *
     * @param context
     * @param path    文件在assets文件夹中的路径
     * @return
     */
    public static String getFileFromAssets(@NonNull Context context, @NonNull String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
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
     * 获取assets目录下的图片
     *
     * @param context
     * @param path    图片在assets文件夹中的路径
     * @return
     */
    public static Bitmap getImageFromAssets(@NonNull Context context, String path) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
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
