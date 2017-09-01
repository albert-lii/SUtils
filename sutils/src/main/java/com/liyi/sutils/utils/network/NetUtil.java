package com.liyi.sutils.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

import com.liyi.sutils.constants.SConstants;
import com.liyi.sutils.utils.log.LogUtil;


/**
 * 网络相关工具类
 */
public class NetUtil {
    private static final String TAG = NetUtil.class.getClass().getSimpleName();

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return true表示网络已连接，false表示未连接
     */
    public static boolean isConnected(@NonNull Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断wifi是否连接
     *
     * @param context
     * @return true表示已连接wifi，false表示未连接wifi
     */
    public static boolean isWifiConnected(@NonNull Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取当前网络的类型
     *
     * @param context Context
     * @return 返回 NETTYPE_WIFI，表示 wifi;
     * 返回 NETTYPE_2G，表示 2g网;
     * 返回 NETTYPE_4G，表示 3g网;
     * 返回 NETTYPE_3G，表示 4g网;
     * 返回 NETTYPE_NONE，表示当前未连接网络
     */
    public static int getNetWorkType(@NonNull Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                return SConstants.NETTYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mobileInfo != null) {
                    LogUtil.i(TAG, "Network Type ========> " + mobileInfo.getSubtypeName());
                    switch (mobileInfo.getType()) {
                        // Mobile phone network
                        case ConnectivityManager.TYPE_MOBILE:
                            switch (mobileInfo.getSubtype()) {
                                case TelephonyManager.NETWORK_TYPE_CDMA:
                                case TelephonyManager.NETWORK_TYPE_GPRS:
                                case TelephonyManager.NETWORK_TYPE_EDGE:
                                case TelephonyManager.NETWORK_TYPE_1xRTT:
                                case TelephonyManager.NETWORK_TYPE_IDEN:
                                    return SConstants.NETTYPE_2G;
                                case TelephonyManager.NETWORK_TYPE_UMTS:
                                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                case TelephonyManager.NETWORK_TYPE_HSDPA:
                                case TelephonyManager.NETWORK_TYPE_HSUPA:
                                case TelephonyManager.NETWORK_TYPE_HSPA:
                                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                                case TelephonyManager.NETWORK_TYPE_EHRPD:
                                case TelephonyManager.NETWORK_TYPE_HSPAP:
                                    return SConstants.NETTYPE_3G;
                                case TelephonyManager.NETWORK_TYPE_LTE:
                                    return SConstants.NETTYPE_4G;
                                default:
                                    //  中国移动 联通 电信 三种3G制式
                                    if (mobileInfo.getSubtypeName().equalsIgnoreCase("TD-SCDMA")
                                            || mobileInfo.getSubtypeName().equalsIgnoreCase("WCDMA")
                                            || mobileInfo.getSubtypeName().equalsIgnoreCase("CDMA2000")) {
                                        return SConstants.NETTYPE_3G;
                                    } else {
                                        return SConstants.NETTYPE_4G;
                                    }
                            }
                    }
                }
            }
        }
        return SConstants.NETTYPE_NONE;
    }
}
