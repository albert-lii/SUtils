package com.liyi.sutils.view;


import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * DialogFragment的基类
 */
public class BaseDialogFragment extends DialogFragment {
    private final float DEF_VISIBLE_ALPHA = 0.5f;
    private final float DEF_WIDTH_PER = 0.75f;
    private final float DEF_HEIGHT_PER = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final boolean DEF_USE_SIZE_PER = false;

    /* 显示时的屏幕背景透明度 */
    private float mVisibleAlpha = DEF_VISIBLE_ALPHA;
    /* 对话框宽所占屏幕的百分比0-1 */
    private float mWidhtPer = DEF_WIDTH_PER;
    /* 对话框高所占屏幕的百分比0-1 */
    private float mHeightPer = DEF_HEIGHT_PER;
    /* 是否使用对话框宽高占屏百分比 */
    private boolean isUseSizePer = DEF_USE_SIZE_PER;

    public <T> T $(int resId, View parent) {
        return (T) parent.findViewById(resId);
    }

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
     * @param dialog
     * @param animStyle
     */
    public void setWindowAnim(Dialog dialog, int animStyle) {
        Window window = dialog.getWindow();
        window.setWindowAnimations(animStyle);
    }

    /**
     * 设置屏幕的背景透明度
     *
     * @param alpha
     */
    public void setVisibleAlpha(float alpha) {
        this.mVisibleAlpha = alpha;
    }

    /**
     * 设置是否使用对话框的宽高占屏百分比
     *
     * @param use
     */
    public void setUseSizePer(boolean use) {
        this.isUseSizePer = use;
    }

    /**
     * 设置对话框的宽度占屏百分比
     *
     * @param per
     */
    public void setWidthPer(float per) {
        mWidhtPer = per;
    }

    /**
     * 设置对话框的高度占屏百分比
     *
     * @param per
     */
    public void setHeightPer(float per) {
        mHeightPer = per;
    }

    /**
     * 设置屏幕的背景透明度
     *
     * @param bgAlpha 0-1（0：屏幕完全透明，1：背景最暗）
     */
    private void setBgAlpha(float bgAlpha) {
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

    /**
     * 设置dialog宽高的占屏比
     *
     * @param wp dialog的宽的占屏比
     * @param hp dialog的高的占屏比
     */
    public void setSizePercent(float wp, float hp) {
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
}
