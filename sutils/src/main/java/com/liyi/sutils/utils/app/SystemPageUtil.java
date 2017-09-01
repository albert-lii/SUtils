package com.liyi.sutils.utils.app;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;


/**
 * 进入指定系统功能界面的工具类
 */
public class SystemPageUtil {

    /**
     * 跳转至系统设置界面
     */
    public static void openSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转到Wifi列表设置界面
     */
    public static void openWifiSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转到移动网络设置界面
     */
    public static void openMobileNetSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转到飞行模式设置界面
     */
    public static void openAirPlaneSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转到蓝牙设置界面
     */
    public static void openBluetoothSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转到NFC设置界面
     */
    @TargetApi(16)
    public static void openNFCSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转到NFC共享设置界面
     */
    @TargetApi(14)
    public static void openNFCShareSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_NFCSHARING_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转位置服务界面
     */
    public static void openGpsSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 根据包名跳转到系统自带的应用程序信息界面
     */
    public static void openAppDetail(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}
