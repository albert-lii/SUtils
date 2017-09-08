package com.liyi.sutils.utils.graphic.glide;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.liyi.sutils.utils.log.LogUtil;

/**
 * glide的管理工具类
 */
public class GlideManager {

    public static GlideRequests getRequests(@NonNull Object obj) {
        GlideRequests requests = null;
        if (obj instanceof Activity) {
            requests = GlideApp.with((Activity) obj);
        } else if (obj instanceof FragmentActivity) {
            requests = GlideApp.with((FragmentActivity) obj);
        } else if (obj instanceof android.app.Fragment) {
            requests = GlideApp.with((android.app.Fragment) obj);
        } else if (obj instanceof android.support.v4.app.Fragment) {
            requests = GlideApp.with((android.support.v4.app.Fragment) obj);
        } else if (obj instanceof Context) {
            requests = GlideApp.with((Context) obj);
        } else if (obj instanceof View) {
            requests = GlideApp.with((View) obj);
        } else {
            LogUtil.e("GlideManager", "Glide中引入的上下文对象格式错误");
        }
        return requests;
    }

    /**
     * 恢复请求，一般在停止滚动的时候
     */
    public static void resumeRequests(Context context) {
        GlideApp.with(context).resumeRequests();
    }

    /**
     * 暂停请求 正在滚动的时候
     */
    public static void pauseRequests(Context context) {
        GlideApp.with(context).pauseRequests();
    }

    /**
     * 清除磁盘缓存
     * 注：需要在子线程中进行
     */
    public static void clearDiskCache(final Context context) {
        // 清理磁盘缓存 需要在子线程中执行
        GlideApp.get(context).clearDiskCache();
    }

    /**
     * 清除内存缓存
     */
    public static void clearMemory(Context context) {
        // 清理内存缓存可以在UI主线程中进行
        GlideApp.get(context).clearMemory();
    }
}
