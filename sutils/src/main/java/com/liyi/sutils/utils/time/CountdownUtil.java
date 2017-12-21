package com.liyi.sutils.utils.time;


import android.os.CountDownTimer;

/**
 * 倒计时工具类
 */
public class CountdownUtil {
    /* 倒计时的间隔时间 */
    private int mIntervalTime;
    /* 倒计时的总时间 */
    private int mTotalTime;
    /* 倒计时是否在运行 */
    private boolean isRunning;
    /* 系统倒计时类 */
    private CountDownTimer mTimer;
    /* 倒计时监听器 */
    private OnCountdownListener mCountdownListener;

    private CountdownUtil() {
        super();
        this.mIntervalTime = 0;
        this.mTotalTime = 0;
        this.isRunning = false;
    }

    public static CountdownUtil newInstance() {
        return new CountdownUtil();
    }

    private void init() {
        mTimer = new CountDownTimer(mTotalTime, mIntervalTime) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (mCountdownListener != null) {
                    mCountdownListener.onRemain(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                isRunning = false;
                if (mCountdownListener != null) {
                    mCountdownListener.onFinish();
                }
            }
        };
    }

    /**
     * 设置倒计时的间隔时间
     *
     * @param intervalTime 间隔时间
     * @return {@link CountdownUtil}
     */
    public CountdownUtil intervalTime(int intervalTime) {
        this.mIntervalTime = intervalTime;
        return this;
    }

    /**
     * 设置倒计时的总时间
     *
     * @param totalTime 总时间
     * @return {@link CountdownUtil}
     */
    public CountdownUtil totalTime(int totalTime) {
        this.mTotalTime = totalTime;
        return this;
    }

    /**
     * 设置倒计时监听器
     *
     * @param listener 倒计时监听器
     * @return {@link CountdownUtil}
     */
    public CountdownUtil callback(OnCountdownListener listener) {
        this.mCountdownListener = listener;
        return this;
    }

    /**
     * 开始倒计时
     */
    public void start() {
        if (mTimer == null) init();
        mTimer.start();
        isRunning = true;
    }

    /**
     * 结束倒计时
     */
    public void stop() {
        if (mTimer != null) mTimer.cancel();
        isRunning = false;
    }

    /**
     * 获取倒计时的间隔时间
     *
     * @return 间隔时间
     */
    public int getIntervalTime() {
        return mIntervalTime;
    }

    /**
     * 获取倒计时的总时间
     *
     * @return 总时间
     */
    public int getTotalTime() {
        return mTotalTime;
    }

    /**
     * 获取倒计时是否正在进行
     *
     * @return {@code true}: 正在进行<br>{@code false}: 已经结束
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 获取倒计时类
     *
     * @return {@link CountDownTimer}
     */
    public CountDownTimer getTimer() {
        return mTimer;
    }

    /**
     * 倒计时监听器
     */
    public interface OnCountdownListener {
        /**
         * 倒计时正在进行时调用的方法
         *
         * @param millisUntilFinished 剩余的时间（毫秒）
         */
        void onRemain(long millisUntilFinished);

        /**
         * 倒计时结束
         */
        void onFinish();
    }
}
