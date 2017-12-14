package com.liyi.sutils.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.liyi.sutils.utils.log.LogUtil;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * GPS 相关工具类
 */
public class GpsUtil {
//    /* Gps权限 */
//    private final String[] GPS_PERMISSIONS = new String[]{
//            Manifest.permission.INTERNET,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION};

    private OnLocationListener mListener;
    private MyLocationListener myLocationListener;
    private GpsStatus.Listener mGpsStatusListener;
    private LocationManager mLocationManager;

    /* gps参数类 */
    private Criteria mCriteria;
    /* 位置变化最小距离：当位置距离变化超过此值时，将更新位置信息（单位：米）*/
    private int mMinDistance;
    /* 位置信息更新周期（单位：毫秒）*/
    private int mMinTime;
    /* 卫星数量 */
    private int mGpsCount;

    public static GpsUtil getInstance() {
        return GpsUtilHolder.INSTANCE;
    }

    private static final class GpsUtilHolder {
        private static final GpsUtil INSTANCE = new GpsUtil();
    }

    private GpsUtil() {
        initParams();
    }

    /***********************************************************************************************
     ****  公用方法
     **********************************************************************************************/

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

    /***********************************************************************************************
     ****  单例操作
     **********************************************************************************************/

    /**
     * 初始化相关参数
     */
    private void initParams() {
        this.mCriteria = setCriteria();
        this.mMinDistance = 1;
        this.mMinTime = 1000;
        this.mGpsCount = 0;
    }

    /**
     * 判断 Gps 是否可用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isGpsEnabled() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) SUtils.getApp().getSystemService(Context.LOCATION_SERVICE);
        }
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断定位是否可用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isLocationEnabled() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) SUtils.getApp().getSystemService(Context.LOCATION_SERVICE);
        }
        return mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                || mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /***********************************************************************************************
     ****  GPS 使用
     **********************************************************************************************/

    /**
     * 注册
     * <p>使用完记得调用{@link #unregister()}</p>
     * <p>如果{@code minDistance}为 0，则通过{@code minTime}来定时更新</p>
     * <p>{@code minDistance}不为 0，则以{@code minDistance}为准</p>
     * <p>两者都为 0，则随时刷新</p>
     *
     * @return {@code true}: 初始化成功<br>{@code false}: 初始化失败
     */
    public boolean register(@NonNull Activity activity) {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        }
        if (!isLocationEnabled()) {
            ToastUtil.show(activity, "无法定位，请打开定位服务");
            return false;
        }
        String provider = mLocationManager.getBestProvider(mCriteria, true);
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (location != null && mListener != null) {
            mListener.getLastKnownLocation(location);
        }
        if (myLocationListener == null) {
            myLocationListener = new MyLocationListener();
        }
        // 定位监听
        // 参数 1，设备：有 GPS_PROVIDER 和 NETWORK_PROVIDER 两种
        // 参数 2，位置信息更新周期，单位毫秒
        // 参数 3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
        // 参数 4，监听
        // 备注：参数 2 和 3，如果参数 3 不为 0，则以参数 3 为准；参数 3 为 0，则通过时间来定时更新；两者为 0，则随时刷新
        // 1 秒更新一次，或最小位移变化超过1米更新一次；
        // 注意：此处更新准确度非常低，推荐在 service 里面启动一个 Thread，在 run 中 sleep(10000); 然后执行 handler.sendMessage(), 更新位置
        mLocationManager.requestLocationUpdates(provider, mMinTime, mMinDistance, myLocationListener);
        return true;
    }

    /**
     * 注销
     */
    public void unregister() {
        if (mLocationManager != null) {
            if (myLocationListener != null) {
                mLocationManager.removeUpdates(myLocationListener);
                myLocationListener = null;
            }
            if (mGpsStatusListener != null) {
                mLocationManager.removeGpsStatusListener(mGpsStatusListener);
                mGpsStatusListener = null;
            }
            mLocationManager = null;
            initParams();
        }
    }

    /**
     * 设置定位参数
     */
    private Criteria setCriteria() {
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

    /***********************************************************************************************
     ****  相关属性设置
     **********************************************************************************************/

    /**
     * 设置 gps 参数类
     *
     * @param criteria
     * @return GpsUtil
     */
    public GpsUtil criteria(Criteria criteria) {
        this.mCriteria = criteria;
        return this;
    }

    /**
     * 设置位置变化的最小距离
     *
     * @param minDistance
     * @return GpsUtil
     */
    public GpsUtil minDistance(int minDistance) {
        this.mMinDistance = minDistance;
        return this;
    }

    /**
     * 设置位置变化的最小时间
     *
     * @param minTime
     * @return GpsUtil
     */
    public GpsUtil minTime(int minTime) {
        this.mMinTime = minTime;
        return this;
    }

    /**
     * 设置定位监听器
     */
    public GpsUtil locationListener(OnLocationListener listener) {
        this.mListener = listener;
        return this;
    }

    /**
     * 设置gps状态监听器
     *
     * @return
     */
    public GpsUtil gpsStatusListener(GpsStatus.Listener listener) {
        this.mGpsStatusListener = listener;
        return this;
    }

    /**
     * 获取gps卫星的数量
     *
     * @return GpsUtil
     */
    public int getGpsCount() {
        return mGpsCount;
    }

    /***********************************************************************************************
     ****  GPS 的相关监听器
     **********************************************************************************************/

    private class MyLocationListener implements LocationListener {
        /**
         * 当坐标改变时触发此函数，如果 Provider 传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        @Override
        public void onLocationChanged(Location location) {
            if (mListener != null) {
                mListener.onLocationChanged(location);
            }
        }

        /**
         * provider 的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   provider 可选包
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (mListener != null) {
                mListener.onStatusChanged(provider, status, extras);
            }
            switch (status) {
                case LocationProvider.AVAILABLE:
                    LogUtil.d("onStatusChanged", "当前GPS状态为可见状态");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    LogUtil.d("onStatusChanged", "当前GPS状态为服务区外状态");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    LogUtil.d("onStatusChanged", "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * provider被enable时触发此函数，比如GPS开启时触发
         */
        @Override
        public void onProviderEnabled(String provider) {

        }

        /**
         * provider被disable时触发此函数，比如GPS被关闭时触发
         */
        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    public interface OnLocationListener {
        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数（位置状态发生改变）
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   provider可选包
         */
        void onStatusChanged(String provider, int status, Bundle extras);

        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        void onLocationChanged(Location location);

        /**
         * 获取最后一次保留的坐标
         *
         * @param location 坐标
         */
        void getLastKnownLocation(Location location);
    }

    public class MyGpsStatusListener implements GpsStatus.Listener {
        private Activity activity;

        public MyGpsStatusListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onGpsStatusChanged(int event) {
            switch (event) {
                // 定位启动
                case GpsStatus.GPS_EVENT_STARTED:
                    break;
                // 第一次定位时间
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    break;
                // 卫星状态改变,收到卫星信息
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    try {
                        mGpsCount = 0;
                        // 取当前状态
                        GpsStatus gpsStauts = mLocationManager.getGpsStatus(null);
                        // 获取卫星颗数的默认最大值
                        int maxSatellites = gpsStauts.getMaxSatellites();
                        // 创建一个迭代器保存所有卫星
                        Iterator<GpsSatellite> it = gpsStauts.getSatellites().iterator();
                        while (it.hasNext() && mGpsCount <= maxSatellites) {
                            GpsSatellite s = it.next();
                            // 可见卫星数量
                            if (s.usedInFix()) {
                                // 已定位卫星数量
                                mGpsCount++;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.e("onGpsStatusChanged", "获取GpsStatus失败");
                    }
                    break;
                // 定位结束
                case GpsStatus.GPS_EVENT_STOPPED:
                    break;
            }
        }
    }
}
