package com.liyi.sutils.utils;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.liyi.sutils.utils.log.LogUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 位置相关工具类
 * <p>
 * 需添加的权限：
 * {@code <uses-permission android:name="android.permission.INTERNET"/>}
 * {@code <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>}
 * {@code <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>}
 * </p>
 */
public class LocationUtil {
    private static LocationManager mLocationManager;
    private static MyLocationListener myLocationListener;
    private static OnLocationChangeListener mLocationChangedListener;
    private static OnProviderStatusListener mProviderStatusListener;

    private LocationUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断 Gps 是否可用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断定位是否可用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLocationEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 设置 Provider 状态监听器
     * <p>请在{@link #register(Context context, long minTime, long minDistance, OnLocationChangeListener listener)}前调用</p>
     *
     * @param listener {@link OnProviderStatusListener}
     */
    public static void setProviderStatusListener(OnProviderStatusListener listener) {
        mProviderStatusListener = listener;
    }

    /**
     * 注册
     * <p>使用完记得调用{@link #unregister()}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>}</p>
     * <p>如果{@code minDistance}为0，则通过{@code minTime}来定时更新；</p>
     * <p>{@code minDistance}不为0，则以{@code minDistance}为准；</p>
     * <p>两者都为0，则随时刷新。</p>
     *
     * @param minTime     位置信息更新周期（单位：毫秒）
     * @param minDistance 位置变化最小距离：当位置距离变化超过此值时，将更新位置信息（单位：米）
     * @param listener    位置刷新的回调接口
     * @return {@code true}: 初始化成功<br>{@code false}: 初始化失败
     */
    public static boolean register(Context context, long minTime, long minDistance, OnLocationChangeListener listener) {
        if (listener == null) return false;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mLocationChangedListener = listener;
        if (!isLocationEnabled(context)) {
            ToastUtil.show("无法定位，请打开定位服务");
            return false;
        }
        String provider = mLocationManager.getBestProvider(getCriteria(), true);
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (location != null) listener.getLastKnownLocation(location);
        if (myLocationListener == null) myLocationListener = new MyLocationListener();
        // 定位监听
        // 参数 1，设备：有 GPS_PROVIDER 和 NETWORK_PROVIDER 两种
        // 参数 2，位置信息更新周期，单位毫秒
        // 参数 3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
        // 参数 4，监听
        // 备注：参数 2 和 3，如果参数 3 不为 0，则以参数 3 为准；参数 3 为 0，则通过时间来定时更新；两者为 0，则随时刷新
        // 1 秒更新一次，或最小位移变化超过1米更新一次；
        // 注意：此处更新准确度非常低，推荐在 service 里面启动一个 Thread，在 run 中 sleep(10000); 然后执行 handler.sendMessage(), 更新位置
        mLocationManager.requestLocationUpdates(provider, minTime, minDistance, myLocationListener);
        return true;
    }

    /**
     * 注销
     */
    public static void unregister() {
        if (mLocationManager != null) {
            if (myLocationListener != null) {
                mLocationManager.removeUpdates(myLocationListener);
                myLocationListener = null;
            }
            mLocationManager = null;
        }
        if (mLocationChangedListener != null) mLocationChangedListener = null;
        if (mProviderStatusListener != null) mProviderStatusListener = null;
    }

    /**
     * 设置定位参数
     */
    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE 比较粗略，Criteria.ACCURACY_FINE 则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(true);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(true);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(true);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    /**
     * 根据经纬度获取地理位置
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return {@link Address}
     */
    public static Address getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(SUtils.getApp(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                return addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据经纬度获取所在国家
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在国家
     */
    public static String getCountry(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getCountryName();
    }

    /**
     * 根据经纬度获取所在地
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在地
     */
    public static String getLocality(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getLocality();
    }

    /**
     * 根据经纬度获取所在街道
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在街道
     */
    public static String getStreet(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getAddressLine(0);
    }

    private static class MyLocationListener implements LocationListener {
        /**
         * 当坐标改变时触发此函数，如果 Provider 传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        @Override
        public void onLocationChanged(Location location) {
            if (mLocationChangedListener != null) {
                mLocationChangedListener.onLocationChanged(location);
            }
        }

        /**
         * Provider 在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   Provider 可选包
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (mLocationChangedListener != null) {
                mLocationChangedListener.onStatusChanged(provider, status, extras);
            }
            switch (status) {
                case LocationProvider.AVAILABLE:
                    LogUtil.d("LocationListener onStatusChanged ========> ", "当前GPS状态为可见状态");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    LogUtil.d("LocationListener onStatusChanged ========> ", "当前GPS状态为服务区外状态");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    LogUtil.d("LocationListener onStatusChanged ========> ", "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * Provider 被 enable 时触发此函数，比如GPS被打开
         */
        @Override
        public void onProviderEnabled(String provider) {
            if (mProviderStatusListener != null) {
                mProviderStatusListener.providerEnabled(provider);
            }
        }

        /**
         * Provider 被 disable 时触发此函数，比如GPS被关闭
         */
        @Override
        public void onProviderDisabled(String provider) {
            if (mProviderStatusListener != null) {
                mProviderStatusListener.providerDisabled(provider);
            }
        }
    }

    public interface OnLocationChangeListener {

        /**
         * 获取最后一次保留的坐标
         *
         * @param location 坐标
         */
        void getLastKnownLocation(Location location);

        /**
         * 当坐标改变时触发此函数，如果 Provider 传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        void onLocationChanged(Location location);

        /**
         * Provider 在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   Provider 可选包
         */
        void onStatusChanged(String provider, int status, Bundle extras);// 位置状态发生改变
    }

    /**
     * Provider 状态监听器
     */
    public interface OnProviderStatusListener {
        /**
         * Provider 被 enable 时触发此函数，比如GPS被打开
         */
        void providerEnabled(String provider);

        /**
         * Provider 被 disable 时触发此函数，比如GPS被关闭
         */
        void providerDisabled(String provider);
    }
}
