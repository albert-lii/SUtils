package com.liyi.sutils.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Service 相关工具类
 * <p>注：Service 是运行在主线程的，想要执行耗时操作，得在 Service 中创建个子线程</p>
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
        List<String> serviceNames = getAllRunningService();
        if (serviceNames == null || serviceNames.size() == 0) return false;
        for (int i = 0; i < serviceNames.size(); i++) {
            if (serviceNames.get(i).equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取所有运行的服务
     *
     * @return 服务名集合
     */
    public static List getAllRunningService() {
        ActivityManager activityManager = (ActivityManager) SUtils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = activityManager.getRunningServices(0x7FFFFFFF);
        List<String> names = new ArrayList<String>();
        if (infos == null || infos.size() == 0) return null;
        for (ActivityManager.RunningServiceInfo aInfo : infos) {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }

    /**
     * 启动服务
     *
     * @param className 完整包名的服务类名
     */
    public static void startService(@Nullable String className) {
        try {
            startService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动服务
     *
     * @param cls 服务类
     */
    public static void startService(Class<?> cls) {
        Intent intent = new Intent(SUtils.getApp(), cls);
        SUtils.getApp().startService(intent);
    }

    /**
     * 停止服务
     *
     * @param className 完整包名的服务类名
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public static boolean stopService(@Nullable String className) {
        try {
            return stopService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 停止服务
     *
     * @param cls 服务类
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public static boolean stopService(Class<?> cls) {
        Intent intent = new Intent(SUtils.getApp(), cls);
        return SUtils.getApp().stopService(intent);
    }

    /**
     * 绑定服务
     *
     * @param className 完整包名的服务类名
     * @param conn      服务连接对象
     * @param flags     绑定选项
     *                  <ul>
     *                  <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                  <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                  <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                  <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                  <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                  <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                  </ul>
     */
    public static void bindService(@Nullable String className, ServiceConnection conn, int flags) {
        try {
            bindService(Class.forName(className), conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定服务
     *
     * @param cls   服务类
     * @param conn  服务连接对象
     * @param flags 绑定选项
     *              <ul>
     *              <li>{@link Context#BIND_AUTO_CREATE}</li>
     *              <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *              <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *              <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *              <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *              <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *              </ul>
     */
    public static void bindService(Class<?> cls, ServiceConnection conn, int flags) {
        Intent intent = new Intent(SUtils.getApp(), cls);
        SUtils.getApp().bindService(intent, conn, flags);
    }

    /**
     * 解绑服务
     *
     * @param conn 服务连接对象
     */
    public static void unbindService(ServiceConnection conn) {
        SUtils.getApp().unbindService(conn);
    }
}
