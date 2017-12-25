package com.liyi.sutils.utils;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

/**
 * 进入指定系统功能界面的相关工具类
 */
public final class SystemSettingUtil {

    private SystemSettingUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 跳转到系统设置界面
     */
    public static void openSettings() {
        final Intent intent = new Intent(Settings.ACTION_SETTINGS);
        SUtils.getApp().startActivity(intent);
    }

    /**
     * 跳转到 Wifi 列表设置界面
     */
    public static void openWifiSettings() {
        final Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        SUtils.getApp().startActivity(intent);
    }

    /**
     * 跳转到移动网络设置界面
     */
    public static void openMobileNetSettings() {
        final Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        SUtils.getApp().startActivity(intent);
    }

    /**
     * 跳转到飞行模式设置界面
     */
    public static void openAirPlaneSettings() {
        final Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        SUtils.getApp().startActivity(intent);
    }

    /**
     * 跳转到蓝牙设置界面
     */
    public static void openBluetoothSettings() {
        final Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        SUtils.getApp().startActivity(intent);
    }

    /**
     * 跳转到 NFC 设置界面
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void openNFCSettings() {
        final Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
        SUtils.getApp().startActivity(intent);
    }

    /**
     * 跳转到 NFC 共享设置界面
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void openNFCShareSettings() {
        final Intent intent = new Intent(Settings.ACTION_NFCSHARING_SETTINGS);
        SUtils.getApp().startActivity(intent);
    }

    /**
     * 跳转位置服务界面
     */
    public static void openGpsSettings() {
        final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        SUtils.getApp().startActivity(intent);
    }
}
