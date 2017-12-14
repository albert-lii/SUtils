package com.liyi.sutils.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;

import com.liyi.sutils.utils.log.LogUtil;

import java.util.List;

/**
 * Service 相关工具类
 */
public class ServiceUtil {
    private static final String TAG = AppUtil.class.getSimpleName();

    private ServiceUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断服务是否存活
     *
     * @param serviceName service 的名称
     * @return {@code true}: 依然存活<br>{@code false}: 已被杀死
     */
    public static boolean isServiceAlive(@NonNull String serviceName) {
        ActivityManager activityManager = (ActivityManager) SUtils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager.getRunningServices(30);
        if (serviceInfos == null || serviceInfos.size() < 1)
            return false;
        for (int i = 0; i < serviceInfos.size(); i++) {
            if (serviceInfos.get(i).service.getClassName().equals(serviceName)) {
                LogUtil.i(TAG, String.format("AppServiceInfo ========> Service %s is running", serviceName));
                return true;
            }
        }
        LogUtil.i(TAG, String.format("AppServiceInfo ========> Service %s is not running", serviceName));
        return false;
    }
}
