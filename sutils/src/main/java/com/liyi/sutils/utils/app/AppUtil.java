package com.liyi.sutils.utils.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.liyi.sutils.utils.log.LogUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;


/**
 * 与APP相关的工具类
 */
public class AppUtil {
    private static final String TAG = AppUtil.class.getSimpleName();
    /**
     * app的状态
     */
    // app运行在前台
    public static final int APPSTATE_FORE = 1;
    // app运行在后台
    public static final int APPSTATE_BACK = 2;
    // app已经被杀死
    public static final int APPSTATE_DEAD = 3;

    /**
     * 获取应用程序名称
     *
     * @param context
     * @return app名称
     */
    public static String getAppName(@NonNull Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用版本名称
     *
     * @param context
     * @return app当前的版本名称（version name）
     */
    public static String getVersionName(@NonNull Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用版本code
     *
     * @param context
     * @return app当前的版本号（version code）
     */
    public static int getVersionCode(@NonNull Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断app是否存活
     *
     * @param context
     * @param packageName 应用程序包名
     * @return true: app依然存活  false: app已被杀死
     */
    public static boolean isAppAlive(@NonNull Context context, @NonNull String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                LogUtil.i(TAG, String.format("AppAliveInfo ========> this %s is running", packageName));
                return true;
            }
        }
        LogUtil.i(TAG, String.format("AppAliveInfo ========> this %s is not running", packageName));
        return false;
    }

    /**
     * 获取app的状态
     *
     * @param context
     * @param packageName 应用程序包名
     * @return APPSTATE_FORE: app运行在前台 | APPSTATE_BACK: app运行在后台 | APPSTATE_DEAD: app已被杀死
     */
    public int getAppSatus(@NonNull Context context, @NonNull String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfos = am.getRunningTasks(20);
        // Determines whether the application is on the top of the stack
        if (taskInfos.get(0).topActivity.getPackageName().equals(packageName)) {
            LogUtil.i(TAG, String.format("AppStateInfo ========> this %s is running onForeground", packageName));
            return APPSTATE_FORE;
        } else {
            // Determine if the application is in the stack
            for (ActivityManager.RunningTaskInfo info : taskInfos) {
                if (info.topActivity.getPackageName().equals(packageName)) {
                    LogUtil.i(TAG, String.format("AppStateInfo ========> this %s is running onBackground", packageName));
                    return APPSTATE_BACK;
                }
            }
            LogUtil.i(TAG, String.format("AppStateInfo ========> this %s is not running", packageName));
            return APPSTATE_DEAD;
        }
    }

    /**
     * 判断服务是否存活
     *
     * @param context
     * @param serviceName service名称
     * @return true: service存活  false: service死亡
     */
    public static boolean isServiceAlive(@NonNull Context context, @NonNull String serviceName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager.getRunningServices(30);
        if (serviceInfos == null || serviceInfos.size() < 1)
            return false;
        for (int i = 0; i < serviceInfos.size(); i++) {
            if (serviceInfos.get(i).service.getClassName().equals(serviceName)) {
                LogUtil.i(TAG, String.format("AppServiceInfo ========> this %s is running", serviceName));
                return true;
            }
        }
        LogUtil.i(TAG, String.format("AppServiceInfo ========> this %s is not running", serviceName));
        return false;
    }

    /**
     * 获取SHA1的值
     *
     * @param context
     * @return SHA1
     */
    public static String getSHA1(@NonNull Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i]).toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            String sha1 = result.substring(0, result.length() - 1);
            LogUtil.i(TAG, String.format("AppSHA1 ========> SHA1 is %s", sha1));
            return sha1;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测某应用程序是否安装
     *
     * @param context
     * @param packageName 应用程序包名
     * @return
     */
    public static boolean isInstalledApp(@NonNull Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            List<PackageInfo> pkgs = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
            for (PackageInfo pkg : pkgs) {
                if ((pkg.packageName).equals(packageName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
