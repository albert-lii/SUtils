package com.liyi.sutils.constants;


/**
 * 常用全局变量
 */
public final class SConstants {
    /**
     * 与SNetUtil相关的参数
     * <p>
     * 网络的类型
     */
    public static final int NETTYPE_WIFI = 1;
    public static final int NETTYPE_2G = 2;
    public static final int NETTYPE_3G = 3;
    public static final int NETTYPE_4G = 4;
    public static final int NETTYPE_NONE = -1;


    /**
     * 与SAppUtil相关的参数
     * <p>
     * app的状态
     */
    // app运行在前台
    public static final int APPSTATE_FORE = 1;
    // app运行在后台
    public static final int APPSTATE_BACK = 2;
    // app已经被杀死
    public static final int APPSTATE_DEAD = 3;


    /**
     * 与glide相关的参数
     */
    // 加载drawable（默认）
    public static final int AS_DRAWABLE = 1;
    // 加载bitmap（可用于gif的静态显示）
    public static final int AS_BITMAP = 2;
    // 加载gif
    public static final int AS_GIF = 3;
    // 加载file
    public static final int AS_FILE = 4;


    /**
     * 与nohttp相关的参数
     */
    public static final int LOAD_START = 1;
    public static final int LOAD_FINISH = 2;
    // 网络请求返回数据为空
    public static final int TIP_EMPTY = 0;
    // 网络请求在onSuccess()中请求失败（已经和服务器连接）
    public static final int TIP_SUCCESS = 1;
    // 网络请求在onFail()中请求失败（未能连接上服务器）
    public static final int TIP_FAIL = 2;
}
