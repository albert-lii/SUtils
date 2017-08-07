package com.liyi.sutils.utils.io;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SJsonFileUtil {

    /**
     * Read the contents of the json file from the assets folder and convert them to strings
     *
     * @param context
     * @param path    The path to the json file in the assets folder
     * @return
     */
    public static String getJsonFromAssets(Context context, String path) {
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
     * Read the contents of the json file and convert them to strings
     *
     * @param path The path to the json file
     * @return
     */
    private String getJson(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream f = new FileInputStream(path);
            BufferedReader bf = new BufferedReader(new InputStreamReader(f));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
