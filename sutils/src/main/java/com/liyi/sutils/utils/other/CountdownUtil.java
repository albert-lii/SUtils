package com.liyi.sutils.utils.other;


import android.os.CountDownTimer;

/**
 * 倒计时工具类
 */
public class CountdownUtil {
    private int mIntervalTime;
    private int mTotalTime;
    private boolean isRunning;

    private CountDownTimer mTimer;
    private OnCountdownListener mCountdownListener;

    private CountdownUtil() {
        super();
        this.mIntervalTime = 0;
        this.mTotalTime = 0;
        this.isRunning = false;
    }

    public CountdownUtil newInstance() {
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
     * @param intervalTime
     * @return
     */
    public CountdownUtil intervalTime(int intervalTime) {
        this.mIntervalTime = intervalTime;
        return this;
    }

    /**
     * 设置倒计时的总时间
     *
     * @param totalTime
     * @return
     */
    public CountdownUtil totalTime(int totalTime) {
        this.mTotalTime = totalTime;
        return this;
    }

    /**
     * 设置倒计时监听器
     *
     * @param listener
     * @return
     */
    public CountdownUtil callback(OnCountdownListener listener) {
        this.mCountdownListener = listener;
        return this;
    }

    public void start() {
        if (mTimer == null) {
            init();
        }
        mTimer.start();
        isRunning = true;
    }

    public void stop() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        isRunning = false;
    }

    public int getIntervalTime() {
        return mIntervalTime;
    }

    public int getTotalTime() {
        return mTotalTime;
    }

    /**
     * 获取倒计时是否正在进行
     *
     * @return
     */
    public boolean isRunning() {
        return isRunning;
    }

    public CountDownTimer getTimer() {
        return mTimer;
    }

    public interface OnCountdownListener {
        void onRemain(long millisUntilFinished);

        void onFinish();
    }
}
