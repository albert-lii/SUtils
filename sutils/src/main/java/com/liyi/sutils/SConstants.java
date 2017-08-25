package com.liyi.sutils;

public final class SConstants {
    /**
     * The parameters of the SNetUtil
     * <p>
     * Network type
     */
    public static final int NETTYPE_WIFI = 1;
    public static final int NETTYPE_2G = 2;
    public static final int NETTYPE_3G = 3;
    public static final int NETTYPE_4G = 4;
    public static final int NETTYPE_NONE = -1;


    /**
     * The parameters of the SAppUtil
     * <p>
     * The running state of the app
     */
    public static final int APPSTATE_FORE = 1;
    public static final int APPSTATE_BACK = 2;
    public static final int APPSTATE_DEAD = 3;


    /**
     * The parameters of the SGlideUtil
     */
    public static final int AS_DRAWABLE = 1;
    public static final int AS_BITMAP = 2;
    public static final int AS_GIF = 3;
    public static final int AS_FILE = 4;


    /**
     * The parameters of the SCallNetUtil
     */
    //
    public static final int LOAD_START = 1;
    public static final int LOAD_FINISH = 2;
    public static final int TIP_EMPTY = 0;
    public static final int TIP_SUCCESS = 1;
    public static final int TIP_FAIL = 2;
}
