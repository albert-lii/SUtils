package com.liyi.sutils.utils.app.permission;


public interface OnPermissionListener {
    /**
     * 用户同意授权
     */
    void onPermissionGranted(int requestCode, String[] grantPermissions);

    /**
     * 用户拒绝授权
     */
    void onPermissionDenied(int requestCode, String[] deniedPermissions, boolean hasAlwaysDenied);
}
