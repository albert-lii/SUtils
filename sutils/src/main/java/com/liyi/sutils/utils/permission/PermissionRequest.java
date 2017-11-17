package com.liyi.sutils.utils.permission;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

/**
 * 权限请求类
 */
public class PermissionRequest {
    private Activity mActivity;
    private int mRequestCode;
    private String[] mPermissions;
    private boolean isAutoShowTip;
    private OnPermissionListener mListener;

    public PermissionRequest(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 添加权限请求的请求码
     *
     * @param requestCode
     * @return
     */
    public PermissionRequest requestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    /**
     * 添加需要申请的权限
     *
     * @param permissions
     * @return
     */
    public PermissionRequest permissions(@NonNull String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    /**
     * 添加权限请求的回调接口
     *
     * @param listener
     * @return
     */
    public PermissionRequest callback(OnPermissionListener listener) {
        this.mListener = listener;
        return this;
    }

    /**
     * 是否自动显示拒绝授权时的提示
     *
     * @param isAutoShowTip
     * @return
     */
    public PermissionRequest autoShowTip(boolean isAutoShowTip) {
        this.isAutoShowTip = isAutoShowTip;
        return this;
    }

    /**
     * 执行权限请求
     */
    public void execute() {
        if (PermissionUtil.isNeedRequest()) {
            String[] deniedPermissions = PermissionUtil.getDeniedPermissions(mActivity, mPermissions);
            if (deniedPermissions.length > 0) {
                ActivityCompat.requestPermissions(mActivity, deniedPermissions, mRequestCode);
            } else {
                if (mListener != null) {
                    mListener.onPermissionGranted(mRequestCode, mPermissions);
                }
            }
        }else {
            if (mListener != null) {
                mListener.onPermissionGranted(mRequestCode, mPermissions);
            }
        }
    }

    /**
     * 获取权限请求码
     *
     * @return
     */
    public int getRequestCode() {
        return mRequestCode;
    }

    /**
     * 获取申请的权限
     *
     * @return
     */
    public String[] getPermissions() {
        return mPermissions;
    }

    /**
     * 获取是否自动显示拒绝授权时的提示
     *
     * @return
     */
    public boolean isAutoShowTip() {
        return isAutoShowTip;
    }

    /**
     * 获取权限请求的回调方法
     *
     * @return
     */
    public OnPermissionListener getPermissionListener() {
        return mListener;
    }
}
