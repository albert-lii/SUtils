package com.liyi.sutils.utils;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liyi
 */
public class SUtils {
    @SuppressLint("StaticFieldLeak")
    private static Application mApplication;
    /* 当前处于栈顶的 Activity */
    private static WeakReference<Activity> mTopActivityWeakRef;
    /* 存储所有存活的 Activity */
    private static List<Activity> mActivityList = new LinkedList<Activity>();
    /* Activity 的生命周期 */
    private static Application.ActivityLifecycleCallbacks mLifecycleCallback = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            // activity 被创建时，向 Activity 列表中添加 activity
            mActivityList.add(activity);
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            // activity 被销毁时，从 Activity 列表中移除 activity
            mActivityList.remove(activity);
        }
    };

    private SUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 初始化工具类
     *
     * @param app 应用对象
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void initialize(@NonNull final Application app) {
        mApplication = app;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            app.registerActivityLifecycleCallbacks(mLifecycleCallback);
        }
    }

    /**
     * 获取应用对象
     *
     * @return Application
     */
    public static Application getApp() {
        if (mApplication != null) {
            return mApplication;
        }
        throw new NullPointerException("you should initialize first");
    }

    /**
     * 设置栈顶的 Activity
     *
     * @param activity
     */
    private static void setTopActivityWeakRef(Activity activity) {
        if (mTopActivityWeakRef == null || !mTopActivityWeakRef.get().equals(activity)) {
            mTopActivityWeakRef = new WeakReference<>(activity);
        }
    }

    /**
     * 获取栈顶的 Activity
     *
     * @return Activity
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static Activity getTopActivity() {
        if (mTopActivityWeakRef != null || mTopActivityWeakRef.get() != null) {
            return mTopActivityWeakRef.get();
        }
        return null;
    }

    /**
     * 获取 Activity 列表
     *
     * @return List<Activity>
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static List<Activity> getActivityList() {
        return mActivityList;
    }
}
