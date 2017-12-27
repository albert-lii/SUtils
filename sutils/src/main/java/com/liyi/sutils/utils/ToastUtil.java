package com.liyi.sutils.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;


/**
 * Toast 相关工具类
 */
public final class ToastUtil {
    /* 默认颜色 */
    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    /* 无效的值 */
    private static final int INVALID_VALUE = -1;
    /* 获取了主线程消息循环的 handler */
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /* Toast 实例 */
    private static Toast mToast;
    /* Toast 的布局 view */
    private static WeakReference<View> mViewWeakRef;
    /* Toast layout 的 id */
    private static int mLayoutId = INVALID_VALUE;
    /* Toast 位置 */
    private static int mGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    /* x 偏移 */
    private static int xOffset = 0;
    /* y 偏移 */
    private static int yOffset = (int) (64 * SUtils.getApp().getResources().getDisplayMetrics().density + 0.5);
    /* Toast 的背景色 */
    private static int mBgColor = COLOR_DEFAULT;
    /* Toast 的背景资源 */
    private static int mBgResource = INVALID_VALUE;
    /* Toast 的文字颜色 */
    private static int mTextColor = COLOR_DEFAULT;

    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 设置 Toast 显示位置
     *
     * @param gravity
     */
    public static void setGravity(int gravity) {
        setGravity(gravity, 0, 0);
    }

    /**
     * 设置 Toast 显示位置
     *
     * @param gravity 位置
     * @param xOffset x 偏移
     * @param yOffset y 偏移
     */
    public static void setGravity(int gravity, int xOffset, int yOffset) {
        ToastUtil.mGravity = gravity;
        ToastUtil.xOffset = xOffset;
        ToastUtil.yOffset = yOffset;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景色
     */
    public static void setBgColor(@ColorInt int backgroundColor) {
        ToastUtil.mBgColor = backgroundColor;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     */
    public static void setBgResource(@DrawableRes int bgResource) {
        ToastUtil.mBgResource = bgResource;
    }

    /**
     * 设置文字颜色
     *
     * @param textColor 颜色
     */
    public static void setTextColor(@ColorInt int textColor) {
        ToastUtil.mTextColor = textColor;
    }

    /**
     * 短时显示 Toast
     *
     * @param text 文本
     */
    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 短时显示 Toast
     *
     * @param resId 资源 Id
     */
    public static void show(@StringRes int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示 Toast
     *
     * @param resId    资源 Id
     * @param duration 显示时间
     */
    public static void show(@StringRes int resId, int duration) {
        show(SUtils.getApp().getResources().getText(resId).toString(), duration);
    }

    /**
     * 短时显示 Toast
     *
     * @param resId 资源 Id
     * @param args  参数
     */
    public static void show(@StringRes int resId, Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示 Toast
     *
     * @param resId    资源 Id
     * @param duration 显示时间
     * @param args     格式化字符串中格式指定符引用的参数
     */
    private static void show(@StringRes int resId, int duration, Object... args) {
        show(String.format(SUtils.getApp().getResources().getString(resId), args), duration);
    }

    /**
     * 短时显示 Toast
     *
     * @param format 格式字符串
     * @param args   参数
     */
    public static void show(@Nullable String format, Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示 Toast
     *
     * @param format   格式字符串
     * @param duration 显示时间
     * @param args     格式字符串中格式指定符引用的参数
     */
    private static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    /**
     * 短时显示自定义 Toast
     *
     * @param layoutId 布局资源 Id
     * @return Toast 的自定义布局
     */
    public static View showCusView(@LayoutRes int layoutId) {
        return showCusView(layoutId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示自定义布局的 Toast
     *
     * @param layoutId 布局资源 Id
     * @param duration 显示时间
     * @return Toast 的自定义布局
     */
    public static View showCusView(@LayoutRes int layoutId, int duration) {
        final View view = getView(layoutId);
        show(view, duration);
        return view;
    }

    /**
     * 短时显示自定义 Toast
     *
     * @param view 自定义布局
     * @return Toast 的自定义布局
     */
    public static View showCusView(View view) {
        return showCusView(view, Toast.LENGTH_SHORT);
    }

    /**
     * 显示自定义布局的 Toast
     *
     * @param view     自定义布局
     * @param duration 显示时间
     * @return Toast 的自定义布局
     */
    public static View showCusView(View view, int duration) {
        show(view, duration);
        return view;
    }


    /**
     * 取消 Toast 显示
     */
    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    /**
     * 显示 Toast
     *
     * @param text     消息文本
     * @param duration 显示时间
     */
    public static void show(final CharSequence text, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                mToast = Toast.makeText(SUtils.getApp(), text, duration);
                TextView tvMessage = (TextView) mToast.getView().findViewById(android.R.id.message);
                TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance);
                tvMessage.setTextColor(mTextColor);
                mToast.setGravity(mGravity, xOffset, yOffset);
                setBg(tvMessage);
                mToast.show();
            }
        });
    }

    /**
     * 显示自定义布局 Toast
     *
     * @param view     Toast 布局
     * @param duration 显示时间
     */
    private static void show(final View view, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                mToast = new Toast(SUtils.getApp());
                mToast.setView(view);
                mToast.setDuration(duration);
                mToast.setGravity(mGravity, xOffset, yOffset);
                setBg();
                mToast.show();
            }
        });
    }

    /**
     * 设置背景
     * <p>Toast 使用的是默认布局</p>
     *
     * @param tvMessage Toast 默认布局中的 textview
     */
    private static void setBg(TextView tvMessage) {
        if (mToast == null) return;
        View toastView = mToast.getView();
        if (mBgResource != INVALID_VALUE) {
            toastView.setBackgroundResource(mBgResource);
            tvMessage.setBackgroundColor(Color.TRANSPARENT);
        } else if (mBgColor != COLOR_DEFAULT) {
            Drawable tvBg = toastView.getBackground();
            Drawable messageBg = tvMessage.getBackground();
            if (tvBg != null && messageBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(mBgColor, PorterDuff.Mode.SRC_IN));
                tvMessage.setBackgroundColor(Color.TRANSPARENT);
            } else if (tvBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(mBgColor, PorterDuff.Mode.SRC_IN));
            } else if (messageBg != null) {
                messageBg.setColorFilter(new PorterDuffColorFilter(mBgColor, PorterDuff.Mode.SRC_IN));
            } else {
                toastView.setBackgroundColor(mBgColor);
            }
        }
    }

    /**
     * 设置背景
     * <p>Toast 使用的是自定义布局</p>
     */
    private static void setBg() {
        if (mToast == null) return;
        View toastView = mToast.getView();
        if (mBgResource != INVALID_VALUE) {
            toastView.setBackgroundResource(mBgResource);
        } else if (mBgColor != COLOR_DEFAULT) {
            Drawable background = toastView.getBackground();
            if (background != null) {
                background.setColorFilter(new PorterDuffColorFilter(mBgColor, PorterDuff.Mode.SRC_IN));
            } else {
                ViewCompat.setBackground(toastView, new ColorDrawable(mBgColor));
            }
        }
    }

    /**
     * 获取布局 view
     *
     * @param layoutId 布局资源 id
     * @return view
     */
    private static View getView(@LayoutRes int layoutId) {
        if (mLayoutId == layoutId) {
            if (mViewWeakRef != null) {
                final View toastView = mViewWeakRef.get();
                if (toastView != null) {
                    return toastView;
                }
            }
        }
        LayoutInflater inflate = (LayoutInflater) SUtils.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View toastView = inflate.inflate(layoutId, null);
        mViewWeakRef = new WeakReference<>(toastView);
        mLayoutId = layoutId;
        return toastView;
    }
}
