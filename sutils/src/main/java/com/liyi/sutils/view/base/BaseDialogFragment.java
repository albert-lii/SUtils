package com.liyi.sutils.view.base;


import android.app.Dialog;
import android.support.annotation.FloatRange;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * DialogFragment 的基类
 */
public abstract class BaseDialogFragment extends DialogFragment {
    private final float DEF_VISIBLE_ALPHA = 0.5f;
    private final float DEF_WIDTH_PER = 0.75f;
    private final float DEF_HEIGHT_PER = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final boolean DEF_USE_SIZE_PER = false;

    /* 显示时的屏幕背景透明度 */
    private float mVisibleAlpha = DEF_VISIBLE_ALPHA;
    /* 对话框宽所占屏幕的百分比 */
    private float mWidhtPer = DEF_WIDTH_PER;
    /* 对话框高所占屏幕的百分比 */
    private float mHeightPer = DEF_HEIGHT_PER;
    /* 是否使用对话框宽高占屏百分比 */
    private boolean isUseSizePer = DEF_USE_SIZE_PER;

    @Override
    public void onStart() {
        super.onStart();
        setBgAlpha(mVisibleAlpha);
        if (isUseSizePer) {
            setSizePercent(mWidhtPer, mHeightPer);
        }
    }

    /**
     * 设置弹框显示消失动画
     *
     * @param dialog    dialog
     * @param animStyle 动画样式
     */
    public void setWindowAnim(Dialog dialog, int animStyle) {
        Window window = dialog.getWindow();
        window.setWindowAnimations(animStyle);
    }

    /**
     * 设置是否使用对话框的宽高占屏百分比
     *
     * @param enable {@code true}: 使用<br>{@code false}: 不使用
     */
    public void setUseSizePerEnabled(boolean enable) {
        this.isUseSizePer = enable;
    }

    /**
     * 设置对话框的宽度占屏百分比
     *
     * @param per 宽度占屏百分比
     */
    public void setWidthPer(@FloatRange(from = 0.0) float per) {
        mWidhtPer = per;
    }

    /**
     * 设置对话框的高度占屏百分比
     *
     * @param per 高度占屏百分比
     */
    public void setHeightPer(@FloatRange(from = 0.0) float per) {
        mHeightPer = per;
    }

    /**
     * 设置dialog宽高的占屏比
     *
     * @param wp dialog 的宽的占屏比
     * @param hp dialog 的高的占屏比
     */
    private void setSizePercent(@FloatRange(from = 0.0) float wp, @FloatRange(from = 0.0) float hp) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            if (wp < 0) {
                wp = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            if (hp < 0) {
                hp = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            dialog.getWindow().setLayout(
                    wp == ViewGroup.LayoutParams.WRAP_CONTENT ? ViewGroup.LayoutParams.WRAP_CONTENT : (int) (dm.widthPixels * wp),
                    hp == ViewGroup.LayoutParams.WRAP_CONTENT ? ViewGroup.LayoutParams.WRAP_CONTENT : (int) (dm.heightPixels * hp));
        }
    }

    /**
     * 设置屏幕的背景透明度
     *
     * @param alpha 透明度
     */
    public void setVisibleAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        this.mVisibleAlpha = alpha;
    }

    /**
     * 设置屏幕的背景透明度
     *
     * @param bgAlpha 0-1（0：屏幕完全透明，1：背景最暗）
     */
    private void setBgAlpha(@FloatRange(from = 0.0, to = 1.0) float bgAlpha) {
        if (bgAlpha < 0) {
            bgAlpha = 0;
        }
        if (bgAlpha > 1) {
            bgAlpha = 1;
        }
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = bgAlpha;
        lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(lp);
    }
}
