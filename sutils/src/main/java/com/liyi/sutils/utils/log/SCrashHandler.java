package com.liyi.sutils.utils.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.liyi.sutils.utils.other.SToastUtil;

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
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 */
public class SCrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = SCrashHandler.class.getClass().getSimpleName();

    // 异常文件的存储路径
    private String mCrashPath;
    // 日志文件名的前缀
    private String mPreFix;

    // CrashHandler实例
    private static SCrashHandler mInstance;
    private static Context mContext;
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // 用来存储设备信息和异常信息
    private Map<String, String> mInfos = new HashMap<String, String>();
    // 用日期来创建日志文件的存放文件夹
    private DateFormat mFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat mFormatter2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private SCrashHandler(Config config) {
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        mCrashPath = config.filePath;
        mPreFix = config.preFix;
    }

    public static void initialize(@NonNull Context context) {
        mContext = context.getApplicationContext();
        mInstance = new SCrashHandler(new Config(context));
    }

    /**
     * 初始化（建议在application中初始化）
     *
     * @param context
     */
    public static void initialize(@NonNull Context context, Config config) {
        mContext = context.getApplicationContext();
        mInstance = new SCrashHandler(config == null ? new Config(context) : config);
    }

    public static final class Config {
        // crash文件存储路径
        private String filePath;
        // 日志文件名的前缀
        private String preFix;
        private Context context;

        public Config(Context context) {
            this.context = context.getApplicationContext();
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 默认路径
                filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                        + "SCrashHandler" + File.separator + this.context.getPackageName();
            }
            preFix = "crash";
        }

        public Config setSdCardStore(String folder) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (TextUtils.isEmpty(folder)) {
                    filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                            + "SCrashHandler" + File.separator + context.getPackageName();
                } else {
                    filePath = Environment.getExternalStorageDirectory().toString() + File.separator + folder;
                }
            }
            return this;
        }

        public Config setFilePreFix(String preFix) {
            if (TextUtils.isEmpty(preFix)) {
                this.preFix = "crash";
            } else {
                this.preFix = preFix;
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
                String appName = pi.applicationInfo.loadLabel(pm).toString();
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                mInfos.put("appName", appName);
                mInfos.put("versionName", versionName);
                mInfos.put("versionCode", versionCode);
            }
            /**
             * 获取手机型号，系统版本，以及SDK版本
             */
            mInfos.put("手机型号:", android.os.Build.MODEL);
            mInfos.put("系统版本", android.os.Build.VERSION.SDK + "");
            mInfos.put("Android版本", android.os.Build.VERSION.RELEASE);
            mInfos.put("手机厂商", android.os.Build.BRAND);
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
            String time = mFormatter2.format(new Date());
            // mPreFix默认是"scrash"
            String fileName = mPreFix + "-" + time + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (!mCrashPath.endsWith(File.separator)) {
                    mCrashPath = mCrashPath + File.separator;
                }
                String path = mCrashPath + mFormatter1;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                fos = new FileOutputStream(path + File.separator + fileName);
                fos.write(sb.toString().getBytes());
            }
            return fileName;
        } catch (Exception e) {
            SLogUtil.e(TAG, "an error occured while writing file ========> " + e);
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
}
