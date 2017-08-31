package com.liyi.sutils.utils.other;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * 系统弹出对话框工具类
 */

public class SAlertDialogUtil {
    private String mNegBtnText, mPosBtnText;
    private boolean cancelable;
    private Context mContext;
    private OnAlertNegativeListener mNegativeListener;
    private OnAlertPositiveListener mPositiveListener;

    public static SAlertDialogUtil newInstance(@NonNull Context context) {
        return new SAlertDialogUtil(context);
    }

    private SAlertDialogUtil(@NonNull Context context) {
        super();
        this.mContext = context;
        cancelable = false;
        mNegBtnText = "取消";
        mPosBtnText = "确定";
    }

    public void showDialog(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setMessage(message)
                .setCancelable(cancelable)
                .setPositiveButton(mPosBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mPositiveListener != null) {
                            mPositiveListener.onPositiveClick(i);
                        }
                    }
                })
                .setNegativeButton(mNegBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mNegativeListener != null) {
                            mNegativeListener.onNegativeClick(i);
                        }
                    }
                })
                .show();
    }

    /**
     * 点击外部区域是否消失
     *
     * @param cancelable
     * @return
     */
    public SAlertDialogUtil setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    /**
     * 取消按钮的文字
     *
     * @param text
     * @return
     */
    public SAlertDialogUtil negativeText(String text) {
        this.mNegBtnText = text;
        return this;
    }

    /**
     * 确定按钮的文字
     *
     * @param text
     * @return
     */
    public SAlertDialogUtil positiveText(String text) {
        this.mPosBtnText = text;
        return this;
    }

    /**
     * 设置取消按钮的点击事件
     *
     * @param listener
     * @return
     */
    public SAlertDialogUtil negative(OnAlertNegativeListener listener) {
        this.mNegativeListener = listener;
        return this;
    }

    /**
     * 设置确定按钮的点击事件
     *
     * @param listener
     * @return
     */
    public SAlertDialogUtil positive(OnAlertPositiveListener listener) {
        this.mPositiveListener = listener;
        return this;
    }

    public interface OnAlertNegativeListener {
        void onNegativeClick(int i);
    }

    public interface OnAlertPositiveListener {
        void onPositiveClick(int i);
    }
}
