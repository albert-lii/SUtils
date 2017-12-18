package com.liyi.sutils.utils.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;

import com.liyi.sutils.utils.SUtils;
import com.liyi.sutils.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UncaughtException 处理类,当程序发生 Uncaught 异常的时候,有该类来接管程序,并记录发送错误报告.
 */
public class CrashUtil implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashUtil.class.getClass().getSimpleName();

    /* 崩溃文件的存储路径 */
    private String mCrashDirPath;
    /* 崩溃文件名的前缀 */
    private String mCrashFilePreFix;

    /* CrashUtil 的实例*/
    private static CrashUtil INSTANCE;
    private static Context mContext;
    /* 系统默认的 UncaughtException 处理类 */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /* 用来存储设备信息和异常信息 */
    private Map<String, String> mInfos = new HashMap<String, String>();
    /* 用日期来创建崩溃文件的存放文件夹 */
    private DateFormat mDirNameFormat = new SimpleDateFormat("yyyy-MM-dd");
    /* 用于格式化日期,作为崩溃文件名的一部分 */
    private DateFormat mFileNameFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private OnHandleCrashCallback mCallback;

    private CrashUtil(Config config) {
        mContext = SUtils.getApp().getApplicationContext();
        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置 CrashUtil 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        mCrashDirPath = config.dirPath;
        mCrashFilePreFix = config.preFix;
        mCallback = config.callback;
    }

    /**
     * 初始化（建议在 application 中初始化）
     */
    public static void initialize() {
        INSTANCE = new CrashUtil(new Config());
    }

    /**
     * 初始化（建议在 application 中初始化）
     *
     * @param config 配置类
     */
    public static void initialize(Config config) {
        INSTANCE = new CrashUtil(config == null ? new Config() : config);
    }

    /**
     * 崩溃文件信息配置类
     */
    public static final class Config {
        // 崩溃文件夹的存储路径
        private String dirPath;
        // 崩溃文件名的前缀
        private String preFix;
        // 崩溃文件处理回调
        private OnHandleCrashCallback callback;

        public Config() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 默认路径
                dirPath = Environment.getExternalStorageDirectory().toString() + File.separator
                        + "CrashUtil" + File.separator
                        + SUtils.getApp().getPackageName();
                preFix = "crash";
            }
        }

        /**
         * 设置崩溃文件夹在 sd 卡中的存储路径
         *
         * @param dir 存储文件夹的路径
         * @return
         */
        public Config setSdCardStore(String dir) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                dirPath = Environment.getExternalStorageDirectory().toString() + File.separator + dir;
            }
            return this;
        }

        /**
         * 设置崩溃文件的前缀
         *
         * @param preFix 崩溃文件的前缀
         * @return
         */
        public Config setFilePreFix(String preFix) {
            if (TextUtils.isEmpty(preFix)) {
                this.preFix = "crash";
            } else {
                this.preFix = preFix;
            }
            return this;
        }

        /**
         * 设置崩溃文件处理回调
         *
         * @param callback
         * @return
         */
        public Config setHandleCrashCallback(OnHandleCrashCallback callback) {
            this.callback = callback;
            return this;
        }
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                // 延迟两秒后退出
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LogUtil.e(TAG, "CrashUtil Error ========> " + e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理、收集错误信息、发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return {@code true}: 处理了该异常信息<br>{@code false}: 未处理该异常信息
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用 Toast 来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtil.show(mContext, "很抱歉,程序出现异常,即将退出");
                Looper.loop();
            }
        }.start();
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        String fileName = saveCrashInfo2File(ex);
        if (mCallback != null) {
            mCallback.handleCrash(fileName);
        }
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String appName = pi.applicationInfo.loadLabel(pm).toString();
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                mInfos.put("应用名称", appName);
                mInfos.put("versionName", versionName);
                mInfos.put("versionCode", versionCode);
            }
            /**
             * 获取手机型号，系统版本，以及 SDK 版本等
             */
            mInfos.put("手机型号", android.os.Build.MODEL);
            mInfos.put("系统版本", android.os.Build.VERSION.SDK + "");
            mInfos.put("Android版本", android.os.Build.VERSION.RELEASE);
            mInfos.put("手机系统定制商", android.os.Build.BRAND);
            mInfos.put("手机硬件制造商", android.os.Build.MANUFACTURER);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(TAG, "an error occured when collect package info ========> " + e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mInfos.put(field.getName(), field.get(null).toString());
                LogUtil.d(TAG, field.getName() + " ========> " + field.get(null));
            } catch (Exception e) {
                LogUtil.e(TAG, "an error occured when collect crash info ========> " + e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : mInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + " = " + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        FileOutputStream fos = null;
        try {
            String time = mFileNameFormat.format(new Date());
            // mCrashFilePreFix 默认是 "crash"
            String fileName = mCrashFilePreFix + "-" + time + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (!mCrashDirPath.endsWith(File.separator)) {
                    mCrashDirPath = mCrashDirPath + File.separator;
                }
                String path = mCrashDirPath + mDirNameFormat;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                fos = new FileOutputStream(path + File.separator + fileName);
                fos.write(sb.toString().getBytes());
            }
            return fileName;
        } catch (Exception e) {
            LogUtil.e(TAG, "an error occured while writing file ========> " + e);
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 崩溃文件处理回调
     */
    public interface OnHandleCrashCallback {
        /**
         * 处理崩溃文件
         *
         * @param fileName 崩溃文件名
         */
        void handleCrash(String fileName);
    }
}
