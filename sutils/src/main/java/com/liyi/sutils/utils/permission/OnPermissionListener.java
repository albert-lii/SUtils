package com.liyi.sutils.utils.permission;

/**
 * 权限请求监听
 */
public interface OnPermissionListener {
    /**
     * 用户同意授权
     *
     * @param requestCode      请求码
     * @param grantPermissions 已被授权的权限
     */
    void onPermissionGranted(int requestCode, String[] grantPermissions);

    /**
     * 用户拒绝授权
     *
     * @param requestCode       请求码
     * @param deniedPermissions 被拒绝授权的权限
     * @param hasAlwaysDenied   用户是否在权限弹出框中勾选了“总是拒绝授权”
     */
    void onPermissionDenied(int requestCode, String[] deniedPermissions, boolean hasAlwaysDenied);
}
