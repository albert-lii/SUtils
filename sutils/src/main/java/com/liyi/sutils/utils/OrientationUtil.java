package com.liyi.sutils.utils;


import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.view.OrientationEventListener;

/**
 * 屏幕方向监听工具类
 */
public class OrientationUtil {
    private volatile static OrientationUtil INSTANCE;
    /* 方向事件监听器 */
    private OrientationEventListener mOrientationEventListener;
    /* 监测屏幕方向的监听器 */
    private OnOrientationListener mOrientationListener;
    private Context mContext;
    /* 传感器等级 */
    private int mRate;

    private OrientationUtil(@NonNull Context context) {
        this.mContext = context.getApplicationContext();
        this.mRate = SensorManager.SENSOR_DELAY_NORMAL;
    }

    public static OrientationUtil getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (OrientationUtil.class) {
                INSTANCE = new OrientationUtil(context);
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化
     */
    private void init() {
        mOrientationEventListener = new OrientationEventListener(mContext, mRate) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (mOrientationListener != null) {
                    mOrientationListener.onOrientationChanged(orientation);
                }
            }
        };
    }

    /**
     * 设置传感器的等级（默认为：SensorManager.SENSOR_DELAY_NORMAL）
     *
     * @param rate
     * @return
     */
    public OrientationUtil rate(int rate) {
        this.mRate = rate;
        return this;
    }

    /**
     * 设置屏幕方向监听方法
     *
     * @param listener
     * @return
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
    }

    /**
     * 判断OrientationEventListener是否可用
     *
     * @return
     */
    public boolean isEnabled() {
        if (mOrientationEventListener == null) {
            return false;
        }
        return mOrientationEventListener.canDetectOrientation();
    }

    /**
     * 监测屏幕方向的监听器
     */
    public interface OnOrientationListener {
        void onOrientationChanged(int orientation);
    }
}
