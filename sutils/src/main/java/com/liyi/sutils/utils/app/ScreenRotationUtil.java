package com.liyi.sutils.utils.app;


import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.view.OrientationEventListener;

/**
 * 屏幕旋转角度监听工具类
 */
public class ScreenRotationUtil {
    private volatile static ScreenRotationUtil mInstance;

    private OrientationEventListener mOrientationListener;
    private OnRotationListener mRotationListener;
    private Context mContext;
    private int mRate;

    private ScreenRotationUtil(@NonNull Context context) {
        this.mContext = context.getApplicationContext();
        this.mRate = SensorManager.SENSOR_DELAY_NORMAL;
    }

    public static ScreenRotationUtil getInstance(@NonNull Context context) {
        if (mInstance == null) {
            synchronized (ScreenRotationUtil.class) {
                mInstance = new ScreenRotationUtil(context);
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     */
    private void init() {
        mOrientationListener = new OrientationEventListener(mContext, mRate) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (mRotationListener != null) {
                    mRotationListener.onRotationChanged(orientation);
                }
            }
        };
    }

    public ScreenRotationUtil rate(int rate) {
        this.mRate = rate;
        return this;
    }

    public ScreenRotationUtil callback(OnRotationListener listener) {
        this.mRotationListener = listener;
        return this;
    }

    /**
     * 开启屏幕旋转角度监测
     */
    public void start() {
        if (mOrientationListener == null) {
            init();
        }
        if (isEnabled()) {
            mOrientationListener.enable();
        } else {
            mOrientationListener.disable();
        }
    }

    /**
     * 停止屏幕旋转角度监测
     */
    public void stop() {
        if (mOrientationListener != null) {
            mOrientationListener.disable();
        }
    }

    /**
     * 判断OrientationEventListener是否可用
     *
     * @return
     */
    public boolean isEnabled() {
        if (mOrientationListener == null) {
            return false;
        }
        return mOrientationListener.canDetectOrientation();
    }

    /**
     * 监测屏幕旋转角度的监听器
     */
    public interface OnRotationListener {
        void onRotationChanged(int orientation);
    }
}
