package com.liyi.sutils.view.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * PopupWindow的基类
 */
public abstract class BasePopuWindow extends PopupWindow {
    private final float DEF_SHOW_ALPHA = 0.5f;
    private final float DEF_DISMISS_ALPHA = 1.0f;

    /* 显示时的屏幕背景透明度 */
    private float mShowAlpha;
    /* 关闭时的屏幕背景透明度 */
    private float mDisAlpha;
    private Context mContext;

    public BasePopuWindow(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public BasePopuWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public BasePopuWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        mShowAlpha = DEF_SHOW_ALPHA;
        mDisAlpha = DEF_DISMISS_ALPHA;
    }

    public <T> T $(int resId, View parent) {
        return (T) parent.findViewById(resId);
    }

    /**
     * 设置弹窗宽高
     *
     * @param width
     * @param height
     */
    public void setPopSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    /**
     * 点击空白处消失
     *
     * @param enabled true: 点击空白处消失 false: 点击空白处不消失
     */
    public void touchOutSideDismiss(boolean enabled) {
        setTouchable(enabled);
        setOutsideTouchable(enabled);
        setBackgroundDrawable(new BitmapDrawable());
    }

    /**
     * 设置弹窗显示时的背景透明度
     *
     * @param alpha
     */
    public void setShowAlpha(float alpha) {
        this.mShowAlpha = alpha;
        setBgAlpha(mShowAlpha);
    }

    /**
     * 设置弹窗关闭时的背景透明度
     *
     * @param alpha
     */
    public void setDisAlpha(float alpha) {
        this.mDisAlpha = alpha;
    }

    /**
     * 设置屏幕的背景透明度
     *
     * @param alpha
     */
    private void setBgAlpha(float alpha) {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = alpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    @Override
    public void dismiss() {
        setBgAlpha(mDisAlpha);
        super.dismiss();
    }

    public Context getContext() {
        return mContext;
    }

    public float getShowAlpha() {
        return mShowAlpha;
    }

    public float getDisAlpha() {
        return mDisAlpha;
    }
}
