package com.liyi.sutils.utils.app.permission;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

public class PermissionRequest {
    private Activity mActivity;
    private int mRequestCode;
    private String[] mPermissions;
    private boolean isAutoShowTip;
    private OnPermissionListener mListener;

    public PermissionRequest(Activity activity) {
        this.mActivity = activity;
    }

    public PermissionRequest requestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public PermissionRequest permissions(@NonNull String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    public PermissionRequest callback(OnPermissionListener callback) {
        this.mListener = callback;
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
        }
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public String[] getPermissions() {
        return mPermissions;
    }

    public boolean isAutoShowTip() {
        return isAutoShowTip;
    }

    public OnPermissionListener getPermissionListener() {
        return mListener;
    }
}
