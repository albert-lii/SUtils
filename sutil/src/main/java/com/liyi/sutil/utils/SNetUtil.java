package com.liyi.sutil.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.liyi.sutil.SConstants;
import com.liyi.sutil.utils.prompt.SLogUtil;

public class SNetUtil {
    private static final String TAG = SNetUtil.class.getClass().getSimpleName();
    /**
     * Determine if the network is connected
     *
     * @param context
     * @return If true, the network is connected or the network is not connected
     */
    public static boolean isConnected(Context context) {
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
     * Determine if wifi is connected
     *
     * @param context
     * @return If true, the wifi is connected or the wifi is not connected.
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Get the current network type
     *
     * @param context Context
     * @return If return NETTYPE_WIFI,it's wifi;
     * and if return NETTYPE_2G, it's 2g;
     * and if return NETTYPE_4G, it's 3g;
     * and if return NETTYPE_3G, it's 4g;
     * and if return NETTYPE_NONE, that means there's no network at the moment.
     */
    public static int getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                return SConstants.NETTYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mobileInfo != null) {
                    SLogUtil.i(TAG,"Network Type ========> "+mobileInfo.getSubtypeName());
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
