package com.liyi.sutils;

import android.graphics.Color;

public final class SConstants {
    /**
     * Network type
     */
    public static final int NETTYPE_WIFI = 1;
    public static final int NETTYPE_2G = 2;
    public static final int NETTYPE_3G = 3;
    public static final int NETTYPE_4G = 4;
    public static final int NETTYPE_NONE = -1;


    /**
     * The running state of the app
     */
    public static final int APPSTATE_FORE = 1;
    public static final int APPSTATE_BACK = 2;
    public static final int APPSTATE_DEAD = 3;


    /**
     * The parameters of the SSystemBarUtil
     */
    public static final String TAG_STATUS_BAR = "StatusBar";
    public static final String TAG_NAVIGATION_BAR = "NavigationBar";
    // Invalid color value
    public static final int SYSTEMBAR_INVALID_VAL = -1;
    // The default status bar color
    public static final int DEFAULT_STATUS_COLOR = 0x10000000;
    // The default navigation bar color
    public static final int DEFAULT_NAVIGATION_COLOR = Color.TRANSPARENT;


    /**
     * The parameters of the SGlideUtil
     */
    public static final int GLIDE_INVALID_VAL = -1;
    public static final int AS_DRAWABLE = 1;
    public static final int AS_BITMAP = 2;
    public static final int AS_GIF = 3;
    public static final int AS_FILE = 4;
}
