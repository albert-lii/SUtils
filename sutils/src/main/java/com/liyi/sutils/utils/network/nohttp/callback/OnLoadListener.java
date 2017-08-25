package com.liyi.sutils.utils.network.nohttp.callback;


public interface OnLoadListener {
    /**
     * @param what
     * @param flag      1: onStart  2: onFinish()
     * @param isLoading
     */
    void onLoading(int what, int flag, boolean isLoading);
}
