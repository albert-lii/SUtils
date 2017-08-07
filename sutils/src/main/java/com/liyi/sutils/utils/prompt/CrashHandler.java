package com.liyi.sutils.utils.prompt;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
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
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getClass().getSimpleName();

    // 异常文件的存储路径
    private String mFolder;

    // CrashHandler实例
    private static CrashHandler mInstance;
    private static Context mContext;
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // 用来存储设备信息和异常信息
    private Map<String, String> mInfos = new HashMap<String, String>();
    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashHandler(Config config) {
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        mFolder = config.mFolder;
    }

    public static void initialize(@NonNull Context context) {
        mContext = context.getApplicationContext();
        mInstance = new CrashHandler(null);
    }

    /**
     * 初始化（建议在application中初始化）
     *
     * @param context
     */
    public static void initialize(@NonNull Context context, Config config) {
        mContext = context.getApplicationContext();
        mInstance = new CrashHandler(config == null ? new Config() : config);
    }

    public static final class Config {
        // crash文件存储路径
        private String mFolder;

        public Config() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                mFolder = Environment.getExternalStorageDirectory().toString() + File.separator + "crash";
            }
        }

        public Config setSdCardStore(String folder) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                mFolder = Environment.getExternalStorageDirectory().toString() + File.separator + folder;
            }
            return this;
        }
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
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
                SLogUtil.e(TAG, "error ========> " + e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                SToastUtil.show(mContext, "很抱歉,程序出现异常,即将退出");
                Looper.loop();
            }
        }.start();
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfo2File(ex);
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
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                mInfos.put("versionName", versionName);
                mInfos.put("versionCode", versionCode);
            }
            /**
             * 获取手机型号，系统版本，以及SDK版本
             */
            mInfos.put("手机型号:", android.os.Build.MODEL);
            mInfos.put("系统版本", "" + android.os.Build.VERSION.SDK);
            mInfos.put("Android版本", android.os.Build.VERSION.RELEASE);
        } catch (PackageManager.NameNotFoundException e) {
            SLogUtil.e(TAG, "an error occured when collect package info ========> " + e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mInfos.put(field.getName(), field.get(null).toString());
                SLogUtil.d(TAG, field.getName() + " ========> " + field.get(null));
            } catch (Exception e) {
                SLogUtil.e(TAG, "an error occured when collect crash info ========> " + e);
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
            sb.append(key + "=" + value + "\n");
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
        try {
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = mFolder;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            SLogUtil.e(TAG, "an error occured while writing file ========> " + e);
        }
        return null;
    }
}
