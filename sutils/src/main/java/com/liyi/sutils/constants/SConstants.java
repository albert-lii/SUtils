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
     * 与AppUtil相关的参数
     * <p>
     * app的状态
     */
    // app运行在前台
    public static final int APPSTATE_FORE = 1;
    // app运行在后台
    public static final int APPSTATE_BACK = 2;
    // app已经被杀死
    public static final int APPSTATE_DEAD = 3;
}
