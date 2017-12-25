package com.liyi.sutils.utils;


import android.content.Context;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;

/**
 * 屏幕方向监听工具类
 */
public class OrientationUtil {
    /* 方向事件监听器 */
    private OrientationEventListener mOrientationEventListener;
    /* 监测屏幕方向的监听器 */
    private OnOrientationListener mOrientationListener;
    /* 传感器等级 */
    private int mRate;
    private Context mContext;

    private OrientationUtil() {
        this.mContext = SUtils.getApp().getApplicationContext();
        this.mRate = SensorManager.SENSOR_DELAY_NORMAL;
    }

    public static OrientationUtil getInstance() {
        return OrientationUtilHolder.INSTANCE;
    }

    private static final class OrientationUtilHolder {
        private static final OrientationUtil INSTANCE = new OrientationUtil();
    }

    /**
     * 初始化
     */
    private void init() {
        mOrientationEventListener = new OrientationEventListener(mContext, mRate) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (mOrientationListener != null) {
                    mOrientationListener.orientationChanged(orientation);
                }
            }
        };
    }

    /**
     * 设置传感器的等级（默认为：SensorManager.SENSOR_DELAY_NORMAL）
     *
     * @param rate 传感器的等级
     *             <ul>
     *             <li>{@link SensorManager#SENSOR_DELAY_FASTEST}: 0</li>
     *             <li>{@link SensorManager#SENSOR_DELAY_GAME}: 1</li>
     *             <li>{@link SensorManager#SENSOR_DELAY_UI}: 2</li>
     *             <li>{@link SensorManager#SENSOR_DELAY_NORMAL}: 3</li>
     *             </ul>
     * @return {@link OrientationUtil}
     */
    public OrientationUtil rate(int rate) {
        this.mRate = rate;
        return this;
    }

    /**
     * 设置屏幕方向监听方法
     *
     * @param listener
     * @return {@link OrientationUtil}
     */
    public OrientationUtil callback(OnOrientationListener listener) {
        this.mOrientationListener = listener;
        return this;
    }

    /**
     * 开启屏幕方向监测
     */
    public void start() {
        if (mOrientationEventListener == null) {
            init();
        }
        if (isEnabled()) {
            mOrientationEventListener.enable();
        } else {
            mOrientationEventListener.disable();
        }
    }

    /**
     * 停止屏幕方向监测
     */
    public void stop() {
        if (mOrientationEventListener != null) {
            mOrientationEventListener.disable();
        }
        mOrientationListener = null;
    }

    /**
     * 判断屏幕方向监听是否可用
     *
     * @return {@code true}: 可用<br>{@code false}: 不可用
     */
    public boolean isEnabled() {
        if (mOrientationEventListener == null) return false;
        return mOrientationEventListener.canDetectOrientation();
    }

    /**
     * 获取方向事件监听器
     *
     * @return orientationEventListener
     */
    public OrientationEventListener getOrientationEventListener() {
        return mOrientationEventListener;
    }

    /**
     * 监测屏幕方向的监听器
     */
    public interface OnOrientationListener {
        /**
         * 监测屏幕方向监听
         *
         * @param orientation 屏幕角度（范围：0 - 360）
         */
        void orientationChanged(int orientation);
    }
}
