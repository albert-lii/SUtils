package com.liyi.sutils.utils.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.liyi.sutils.utils.AppUtil;

import java.util.ArrayList;

/**
 * 权限管理相关工具类
 * <p>
 * <p>从 Android 6.0 开始，应用中需要的权限需要用户授权</p>
 * <p>在 Android 6.0 以下，应用会强行获取所有的权限，不必用户授权</p>
 */
public final class PermissionUtil {
    /* 权限请求列表 */
    private static ArrayList<PermissionRequest> mRequestList = new ArrayList<PermissionRequest>();

    /**
     * 绑定 activity
     *
     * @param activity activity
     * @return {@link PermissionRequest}
     */
    public static PermissionRequest with(@NonNull Activity activity) {
        return addRequest(activity);
    }

    /**
     * 绑定 fragment
     *
     * @param fragment fragment
     * @return {@link PermissionRequest}
     */
    public static PermissionRequest with(@NonNull Fragment fragment) {
        return addRequest(fragment.getActivity());
    }

    /**
     * 绑定 android.app.Fragment
     *
     * @param fragment fragment
     * @return {@link PermissionRequest}
     */
    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    public static PermissionRequest with(@NonNull android.app.Fragment fragment) {
        return addRequest(fragment.getActivity());
    }

    /**
     * 添加权限请求
     *
     * @param activity activity
     * @return {@link PermissionRequest}
     */
    private static PermissionRequest addRequest(@NonNull Activity activity) {
        PermissionRequest request = new PermissionRequest(activity);
        if (mRequestList == null) {
            mRequestList = new ArrayList<PermissionRequest>();
        }
        mRequestList.add(request);
        return request;
    }

    /**
     * 处理请求授权后返回的结果
     * <p>此方法需要放在 onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,int[] grantResults) 方法中执行</p>
     *
     * @param activity     activity
     * @param requestCode  请求码
     * @param permissions  申请的所有权限
     * @param grantResults 授权结果
     */
    public static void handleRequestPermissionsResult(@NonNull Activity activity, int requestCode, @NonNull String[] permissions, int[] grantResults) {
        // 判断权限请求列表中是否有权限请求
        if (mRequestList != null && mRequestList.size() > 0) {
            PermissionRequest currentReq = null;
            for (PermissionRequest request : mRequestList) {
                // 遍历权限请求列表，如果有权限请求的 requestCode 与返回的 requestCode 一致，
                // 且权限请求的申请的所有权限与返回的申请的所有权限一致，则从权限请求列表中提取出该权限请求
                if ((requestCode == request.getRequestCode()) && permissions.equals(request.getPermissions())) {
                    currentReq = request;
                    break;
                }
            }
            if (currentReq != null) {
                if (currentReq.getPermissionListener() != null) {
                    // 创建被拒绝授权的权限列表
                    ArrayList<String> deniedPermissions = new ArrayList<String>();
                    for (int i = 0; i < grantResults.length; i++) {
                        // 如果授权结果列表中的值不是已授权，则将权限加入被拒绝授权的权限列表
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permissions[i]);
                        }
                    }
                    // 如果所有权限都授权成功,调用授权成功的回调
                    if (deniedPermissions.size() <= 0) {
                        if (currentReq.getPermissionListener() != null) {
                            currentReq.getPermissionListener().onPermissionGranted(requestCode, permissions);
                        }
                    } else {
                        String[] perms = deniedPermissions.toArray(new String[deniedPermissions.size()]);
                        // 判断是否有权限被用户在权限弹框中勾选了总是拒绝授权
                        boolean hasAlwaysDenied = hasAlwaysDeniedPermission(activity, perms);
                        if (currentReq.isAutoShowTip() && hasAlwaysDenied) {
                            showTipDialog(activity, null);
                        }
                        if (currentReq.getPermissionListener() != null) {
                            // 调用授权失败的回调
                            currentReq.getPermissionListener().onPermissionDenied(requestCode, perms, hasAlwaysDenied);
                        }
                    }
                }
                // 在处理完权限请求结果后，从权限请求列表中移除该权限请求
                mRequestList.remove(currentReq);
            }
        }
    }

    /**
     * 判断是否需要申请权限
     *
     * @return {@code true}: 需要申请权限<br>{@code false}: 不需要申请权限
     */
    public static boolean isNeedRequest() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 判断用户是否已经拥有指定权限
     *
     * @param context     上下文对象
     * @param permissions 需要申请的权限
     * @return {@code true}: 已获取权限<br>{@code false}: 未获取权限
     */
    public static boolean hasPermissions(@NonNull Context context, @NonNull String... permissions) {
        if (isNeedRequest()) {
            for (String p : permissions)
                if (!(ContextCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_GRANTED))
                    return false;
            return true;
        }
        return true;
    }

    /**
     * 获取缺少的权限
     *
     * @param context     上下文对象
     * @param permissions 需要申请的所有权限
     * @return 需要申请的所有权限中未获取的权限
     */
    public static String[] getDeniedPermissions(@NonNull Context context, @NonNull String... permissions) {
        ArrayList<String> permissionList = new ArrayList<String>();
        if (isNeedRequest()) {
            for (String p : permissions) {
                if (ContextCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_DENIED) {
                    permissionList.add(p);
                }
            }
        }
        return permissionList.toArray(new String[permissionList.size()]);
    }

    /**
     * 判断是否在自动弹出的权限弹框中勾选了总是拒绝授权
     *
     * @param activity          activity
     * @param deniedPermissions 需要申请的权限
     * @return {@code true}: 在权限弹出框中勾选了“总是拒绝授权”<br>{@code false}: 在权限弹出框中未勾选“总是拒绝授权”
     */
    public static boolean hasAlwaysDeniedPermission(@NonNull Activity activity, @NonNull String... deniedPermissions) {
        if (isNeedRequest()) {
            for (String permission : deniedPermissions)
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                    return true;
            return false;
        }
        return false;
    }

    /**
     * 显示提示框，用于在缺少权限的情况下，用户拒绝授权，给用户弹出提示信息
     *
     * @param context 上下文对象
     * @param message 提示框中的内容
     */
    public static void showTipDialog(@NonNull final Context context, @NonNull String message) {
        if (isNeedRequest()) {
            new AlertDialog.Builder(context)
                    .setTitle("提示信息")
                    .setMessage(TextUtils.isEmpty(message) ?
                            "当前应用缺少必要权限，可能无法正常使用所有功能。请单击【确定】按钮前往设置中心进行权限授权"
                            : message)
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            AppUtil.openAppSettings();
                        }
                    }).show();
        }
    }
}
