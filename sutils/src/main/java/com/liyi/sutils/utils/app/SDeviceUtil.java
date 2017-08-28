package com.liyi.sutils.utils.app;


import android.content.Context;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class SDeviceUtil {
    /**
     * Get the unique ID of the phone, the device string number
     *
     * @param context
     * @return
     */
    public static String getDeviceID(@NonNull Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * Gets the current phone system version number
     *
     * @return System version number
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * Acquisition of equipment model
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * Acquiring equipment manufacturer
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * Gets the language of the current device system.
     *
     * @return Returns the current system language.For example, the current setting is "Chinese - China", and the "zh-cn" is returned.
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * Gets the list of languages on the current system (Locale list)
     *
     * @return Language list
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * Determine whether the phone is root
     */
    public static boolean isRoot() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        if (new File(binPath).exists() && isExecutable(binPath)) {
            return true;
        }
        if (new File(xBinPath).exists() && isExecutable(xBinPath)) {
            return true;
        }
        return false;
    }

    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }
}
